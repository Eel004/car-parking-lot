package com.example.carparkinglot.infrastructure.onemap;

import com.example.carparkinglot.app.component.LocationConverter;
import com.example.carparkinglot.infrastructure.hdb.response.CarParkAvailabilityResponse;
import com.example.carparkinglot.infrastructure.onemap.response.ConverterResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OneMapConverter implements LocationConverter {

  private final RestTemplate restTemplate;

  @Value("${infra.onemap.url}")
  private String oneMapUrl;

  @Value("${infra.onemap.token}")
  private String oneMapToken;

  public OneMapConverter(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ConverterResponse fromSvy21ToWgs84O(Double latitude, Double longitude) {
    log.info("method=fromSvy21ToWgs84O");
    var headers = new HttpHeaders();
    headers.add("Authorization", oneMapToken);
    var url = String.format("%s?X=%s&Y=%s", oneMapUrl, latitude, longitude);
    var responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), ConverterResponse.class);
    return responseEntity.getBody();
  }

}
