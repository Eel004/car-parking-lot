package com.example.carparkinglot.domain.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class ParkingLot {

  @CsvBindByName(column = "car_park_no")
  private String carParNo;

  @CsvBindByName(column = "address")
  private String address;

  @CsvBindByName(column = "x_coord")
  private Double xCoord;

  @CsvBindByName(column = "y_coord")
  private Double yCoord;

  @CsvBindByName(column = "car_park_type")
  private String carParkType;

  @CsvBindByName(column = "type_of_parking_system")
  private String typeOfParkingSystem;

  @CsvBindByName(column = "short_term_parking")
  private String shortTermParking;

  @CsvBindByName(column = "free_parking")
  private String freeParking;

  @CsvBindByName(column = "night_parking")
  private String nightParking;

  @CsvBindByName(column = "car_park_decks")
  private Integer carParkDecks;

  @CsvBindByName(column = "gantry_height")
  private Double gantryHeight;

  @CsvBindByName(column = "car_park_basement")
  private String carParBasement;

}
