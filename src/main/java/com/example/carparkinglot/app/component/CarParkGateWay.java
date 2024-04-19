package com.example.carparkinglot.app.component;

import com.example.carparkinglot.infrastructure.hdb.response.CarParkAvailabilityResponse;

public interface CarParkGateWay {

  CarParkAvailabilityResponse updateCarParkAvailability();

}
