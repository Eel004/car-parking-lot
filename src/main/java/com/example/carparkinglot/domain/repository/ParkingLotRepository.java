package com.example.carparkinglot.domain.repository;

import com.example.carparkinglot.domain.entity.ParkingLot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface ParkingLotRepository extends CrudRepository<ParkingLot, String> {

  @Modifying
  @Query("""
      UPDATE ParkingLot pl
        SET pl.totalLots = :total_lots,
            pl.lotType = :lot_type,
            pl.lotsAvailable = :lots_available,
             pl.updateDateTime = :update_datetime
        WHERE pl.carParkNo = :car_park_no
      """)
  void updateCarParkAvailability(@Param(value = "total_lots") Integer totalLots,
                                 @Param(value = "lot_type") String lotType,
                                 @Param(value = "lots_available") Integer lotAvailable,
                                 @Param(value = "update_datetime") OffsetDateTime updateDateTime,
                                 @Param(value = "car_park_no") String carParkNo);

  String HAVERSINE_PART = """
        (6371 * acos(cos(radians(:latitude)) * cos(radians(pl.yCoord)) * cos(radians(pl.xCoord)
         - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(pl.yCoord))))
      """;
  @Query("SELECT pl FROM ParkingLot pl WHERE "+ HAVERSINE_PART + " < :distance ORDER BY " + HAVERSINE_PART + " ASC")
  List<ParkingLot> findParkingLotsByLocation(@Param("latitude") final double yCoord,
                                             @Param("longitude") final double xCoord,
                                             @Param("distance") final double distance,
                                             Pageable pageable);
}
