package com.example.carparkinglot.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "parking_lot", schema = "wego_assignment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "carParkNo")
public class ParkingLot {

  @Id
  @Column(name = "car_park_no", nullable = false)
  private String carParkNo;

  @Column(name = "address")
  private String address;

  @Column(name = "x_coord")
  private Double xCoord;

  @Column(name = "y_coord")
  private Double yCoord;

  @Column(name = "car_park_type")
  private String carParkType;

  @Column(name = "type_of_parking_system")
  private String typeOfParkingSystem;

  @Column(name = "short_term_parking")
  private String shortTermParking;

  @Column(name = "free_parking")
  private String freeParking;

  @Column(name = "night_parking")
  private String nightParking;

  @Column(name = "car_park_decks")
  private Integer carParkDecks;

  @Column(name = "gantry_height")
  private Double gantryHeight;

  @Column(name = "car_park_basement")
  private String carParBasement;

  @Column(name = "total_lots")
  private Integer totalLots;

  @Column(name = "lot_type")
  private String lotType;

  @Column(name = "lots_available")
  private Integer lotsAvailable;

  @Column(name = "update_datetime")
  private OffsetDateTime updateDateTime;

}
