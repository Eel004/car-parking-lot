package com.example.carparkinglot.infrastructure.restful;

import com.example.carparkinglot.domain.repository.ParkingLotRepository;
import com.example.carparkinglot.infrastructure.hdb.HdbCarParkProvider;
import com.example.carparkinglot.infrastructure.hdb.response.ParkingLotsResponse;
import com.example.carparkinglot.infrastructure.onemap.OneMapConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
public class CarParkController {

  private final HdbCarParkProvider hdbCarParkProvider;

  private final ParkingLotRepository parkingLotRepository;

  private final OneMapConverter oneMapConverter;

  @Autowired
  public CarParkController(HdbCarParkProvider hdbCarParkProvider,
                           ParkingLotRepository parkingLotRepository, OneMapConverter oneMapConverter) {
    this.hdbCarParkProvider = hdbCarParkProvider;
    this.parkingLotRepository = parkingLotRepository;
    this.oneMapConverter = oneMapConverter;
  }

  @GetMapping("/car-availability")
  public ResponseEntity<String> checkCarParkAvailability() {
    hdbCarParkProvider.updateCarParkAvailability();
    return ResponseEntity.ok("OK");
  }

  @GetMapping("/carparks/nearest")
  public ResponseEntity<List<ParkingLotsResponse>> findNearestParkingLots(@RequestParam("latitude") Double latitude,
                                                                          @RequestParam("longitude") Double longitude,
                                                                          @RequestParam(value = "page") Integer page,
                                                                          @RequestParam(value = "per_page") Integer perPage) {
    var pageable = PageRequest.of(page, perPage);
    var parkingLotsByLocation = parkingLotRepository.findParkingLotsByLocation(latitude, longitude, 3, pageable);
    List<ParkingLotsResponse> parkingLotsResponses = parkingLotsByLocation
        .stream()
        .map(pl -> ParkingLotsResponse.builder()
            .address(pl.getAddress())
            .latitude(pl.getYCoord())
            .longitude(pl.getXCoord())
            .totalLost(pl.getTotalLots())
            .availableLots(pl.getLotsAvailable())
            .build())
        .filter(resp -> resp.getAvailableLots() != null && resp.getAvailableLots() != 0)
        .toList();

    return ResponseEntity.ok(parkingLotsResponses);
  }

}
