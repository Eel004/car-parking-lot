package com.example.carparkinglot.domain.exception;

public enum DomainCode {

  GATEWAY_ERROR(500, "gateway error"),
  MISSING_PARAM(400, "Missing params");

  private final int value;
  private final String message;

  DomainCode(int value, String message) {
    this.value = value;
    this.message = message;
  }

  public int getValue() {
    return value;
  }

  public String getMessage() {
    return message;
  }

}
