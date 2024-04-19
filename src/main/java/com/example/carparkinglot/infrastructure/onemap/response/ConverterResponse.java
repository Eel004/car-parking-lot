package com.example.carparkinglot.infrastructure.onemap.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConverterResponse {

  private Double latitude;

  private Double longitude;

}
