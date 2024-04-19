package com.example.carparkinglot.infrastructure.hdb.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotsResponse {

  @JsonProperty("address")
  private String address;

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Double longitude;

  @JsonProperty("total_lots")
  private Integer totalLost;

  @JsonProperty("available_lots")
  private Integer availableLots;

}
