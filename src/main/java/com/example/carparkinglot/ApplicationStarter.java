package com.example.carparkinglot;

import com.example.carparkinglot.infrastructure.hdb.HdbCarParkProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
@Slf4j
public class ApplicationStarter implements ApplicationListener<ApplicationReadyEvent> {

  private HdbCarParkProvider hdbCarParkProvider;

  @Autowired
  public ApplicationStarter(HdbCarParkProvider hdbCarParkProvider) {
    this.hdbCarParkProvider = hdbCarParkProvider;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.info("Start loading data =>>>>>>>>> ");
    try {
      hdbCarParkProvider.loadDataFromCsv();
      hdbCarParkProvider.updateCarParkAvailability();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
