package com.example.carparkinglot.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "car_park_availability", schema = "wego_assignment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "carParkNumber")
public class CarParkAvailability {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private String id;

  @Column(name = "car_park_number")
  private String carParkNumber;

  @Column(name = "update_datetime")
  private OffsetDateTime updateDateTime;

  @Column(name = "total_lots")
  private Integer totalLots;

  @Column(name = "lot_type")
  private String lotType;

  @Column(name = "lots_available")
  private Integer lotsAvailable;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "car_park_no")
  private ParkingLot parkingLot;

}
