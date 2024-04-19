package com.example.carparkinglot.infrastructure.hdb;

import com.example.carparkinglot.app.component.CarParkProvider;
import com.example.carparkinglot.domain.csv.ParkingLot;
import com.example.carparkinglot.domain.repository.ParkingLotRepository;
import com.example.carparkinglot.infrastructure.onemap.OneMapConverter;
import com.example.carparkinglot.infrastructure.svy21.LatLonCoordinate;
import com.example.carparkinglot.infrastructure.svy21.Svy21;
import com.example.carparkinglot.infrastructure.svy21.Svy21Coordinate;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class HdbCarParkProvider implements CarParkProvider {

  private final ParkingLotRepository parkingLotRepository;

  private final HdbGateway hdbGateway;

  private final OneMapConverter oneMapConverter;

  @Autowired
  public HdbCarParkProvider(ParkingLotRepository parkingLotRepository,
                            HdbGateway hdbGateway, OneMapConverter oneMapConverter) {
    this.parkingLotRepository = parkingLotRepository;
    this.hdbGateway = hdbGateway;
    this.oneMapConverter = oneMapConverter;
  }

  @Override
  public void loadDataFromCsv() throws FileNotFoundException {
    log.info("method=loadDataFromCsv, start loading data...");
    var resourceAsStream = this.getClass()
        .getClassLoader()
        .getResourceAsStream("data/HDBCarparkInformation.csv");

    assert resourceAsStream != null;

    var parkingLots = new CsvToBeanBuilder<ParkingLot>(new InputStreamReader(resourceAsStream,
        StandardCharsets.UTF_8))
        .withType(ParkingLot.class)
        .build()
        .parse();

    log.info("method=loadDataFromCsv, loaded, start save data...");
    parkingLots.stream()
        .map(this::csvToEntity)
        .forEach(parkingLotRepository::save);
  }

  @Override
  @Transactional
  public void updateCarParkAvailability() {
    log.info("method=updateCarParkAvailability, updating car park availability");
    var response = hdbGateway.updateCarParkAvailability();

    var carParkAvailabilityResponse = response.getItems().iterator().next();
    var carParkDataList = carParkAvailabilityResponse.getCarParkDataList();

    carParkDataList
        .forEach(carParkData -> {
          var carParkInfo = carParkData.getCarParkInfo().iterator().next();
          var carParkNumber = carParkData.getCarParkNumber();
          var updateDatetime = carParkData.getUpdateDatetime();
          parkingLotRepository.updateCarParkAvailability(Integer.parseInt(carParkInfo.getTotalLots()),
              carParkInfo.getLotType(),
              Integer.parseInt(carParkInfo.getLotsAvailable()),
              fromText(updateDatetime),
              carParkNumber);
        });
  }

  private OffsetDateTime fromText(String dateTime) {
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
    return localDateTime.toInstant(ZoneOffset.UTC)
        .atOffset(ZoneOffset.UTC);
  }

  private com.example.carparkinglot.domain.entity.ParkingLot csvToEntity(ParkingLot parkingLot) {
//    var converterResponse = oneMapConverter.fromSvy21ToWgs84O(parkingLot.getXCoord(), parkingLot.getYCoord());
    Svy21Coordinate svy21Coordinate = new Svy21Coordinate(parkingLot.getYCoord(), parkingLot.getXCoord());
    LatLonCoordinate latLonCoordinate = svy21Coordinate.asLatLon();
    return com.example.carparkinglot.domain.entity.ParkingLot.builder()
        .carParkNo(parkingLot.getCarParNo())
        .address(parkingLot.getAddress())
        .xCoord(latLonCoordinate.getLongitude())
        .yCoord(latLonCoordinate.getLatitude())
        .carParkType(parkingLot.getCarParkType())
        .typeOfParkingSystem(parkingLot.getTypeOfParkingSystem())
        .shortTermParking(parkingLot.getShortTermParking())
        .freeParking(parkingLot.getFreeParking())
        .nightParking(parkingLot.getNightParking())
        .carParkDecks(parkingLot.getCarParkDecks())
        .gantryHeight(parkingLot.getGantryHeight())
        .carParBasement(parkingLot.getCarParBasement())
        .lotType(null)
        .totalLots(null)
        .lotsAvailable(null)
        .updateDateTime(null)
        .build();
  }

}
