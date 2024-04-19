package com.example.carparkinglot.infrastructure.restful;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestfulApiError {

  private Integer errorId;

  private String message;
}
