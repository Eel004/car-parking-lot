package com.example.carparkinglot.app.component;

import java.io.FileNotFoundException;

public interface CarParkProvider {

  void loadDataFromCsv() throws FileNotFoundException;

  void updateCarParkAvailability();

}
