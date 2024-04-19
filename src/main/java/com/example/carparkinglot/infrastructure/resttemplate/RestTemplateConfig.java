package com.example.carparkinglot.infrastructure.resttemplate;

import lombok.Data;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Data
@Configuration
public class RestTemplateConfig {

  private int maxConnectionTotal = 200;

  private int maxConnectionPerRoute = 20;

  private int connectionTimeoutMs = 5_000;

  private int readTimeoutMs = 300_000;

  private int connectionRequestTimeoutMs = 5_000;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                   MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
    var restTemplate = restTemplateBuilder
        .requestFactory(this::httpComponentsClientHttpRequestFactory)
        .build();

    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

    return restTemplate;
  }

  @Bean
  public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
    clientHttpRequestFactory.setConnectTimeout(connectionTimeoutMs);
    clientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeoutMs);

    return clientHttpRequestFactory;
  }

  private CloseableHttpClient httpClient() {
    var socketConfig = SocketConfig.custom()
        .setSoTimeout(readTimeoutMs, TimeUnit.MILLISECONDS)
        .build();

    var httpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder.create()
        .setMaxConnTotal(maxConnectionTotal)
        .setMaxConnPerRoute(maxConnectionPerRoute)
        .setDefaultSocketConfig(socketConfig)
        .build();

    return HttpClients
        .custom()
        .disableAuthCaching()
        .disableDefaultUserAgent()
        .disableCookieManagement()
        .disableConnectionState()
        .setConnectionManager(httpClientConnectionManager)
        .setRedirectStrategy(new DefaultRedirectStrategy())
        .evictExpiredConnections()
        .useSystemProperties()
        .build();
  }

}
