package com.example.carparkinglot.infrastructure.hdb.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarParkAvailabilityResponse {

  @JsonProperty("items")
  private List<Availability> items;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Availability {

    @JsonProperty("carpark_data")
    List<CarParkData> carParkDataList;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CarParkData {

    @JsonProperty("carpark_info")
    private List<CarParkInfo> carParkInfo;

    @JsonProperty("carpark_number")
    private String carParkNumber;

    @JsonProperty("update_datetime")
    private String updateDatetime;

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CarParkInfo {
    @JsonProperty("total_lots")
    private String totalLots;

    @JsonProperty("lot_type")
    private String lotType;

    @JsonProperty("lots_available")
    private String lotsAvailable;
  }
}
