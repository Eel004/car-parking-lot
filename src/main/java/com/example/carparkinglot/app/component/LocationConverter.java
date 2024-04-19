package com.example.carparkinglot.app.component;

import com.example.carparkinglot.infrastructure.onemap.response.ConverterResponse;

public interface LocationConverter {

  ConverterResponse fromSvy21ToWgs84O(Double latitude, Double longitude);

}
