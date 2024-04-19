package com.example.carparkinglot.infrastructure.taskexecutor;

import com.example.carparkinglot.infrastructure.hdb.HdbCarParkProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CarParkAvailabilityScheduler {

  private final TaskExecutor taskExecutor;

  private final HdbCarParkProvider hdbCarParkProvider;

  @Autowired
  public CarParkAvailabilityScheduler(TaskExecutor taskExecutor,
                                      HdbCarParkProvider hdbCarParkProvider) {
    this.taskExecutor = taskExecutor;
    this.hdbCarParkProvider = hdbCarParkProvider;
  }

  @Scheduled(cron = "0 0/1 * * * *")
  public void checkAvailability() {
    log.info("method=checkAvailability, starting task...");
    taskExecutor.execute(hdbCarParkProvider::updateCarParkAvailability);
  }

}
