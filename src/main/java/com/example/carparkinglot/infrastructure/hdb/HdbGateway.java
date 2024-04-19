package com.example.carparkinglot.infrastructure.hdb;

import com.example.carparkinglot.app.component.CarParkGateWay;
import com.example.carparkinglot.infrastructure.hdb.response.CarParkAvailabilityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class HdbGateway implements CarParkGateWay {

  private final RestTemplate restTemplate;

  @Value("${infra.hdb.carpark-availability}")
  private String hdbCarParkCheckUrl;

  public HdbGateway(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public CarParkAvailabilityResponse updateCarParkAvailability() {
    log.info("method=updateCarParkAvailability");
    var headers = new HttpHeaders();
    var responseEntity = restTemplate.exchange(hdbCarParkCheckUrl, HttpMethod.GET, new HttpEntity<>(headers), CarParkAvailabilityResponse.class);
    return responseEntity.getBody();
  }

}
