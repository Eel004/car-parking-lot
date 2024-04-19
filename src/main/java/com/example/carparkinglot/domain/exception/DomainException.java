package com.example.carparkinglot.domain.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

  private final DomainCode code;

  public DomainException(DomainCode code, Object... args) {
    super(String.format(code.getMessage(), args));
    this.code = code;
  }

}
