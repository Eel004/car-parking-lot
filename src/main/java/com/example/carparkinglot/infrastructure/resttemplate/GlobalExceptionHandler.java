package com.example.carparkinglot.infrastructure.resttemplate;

import com.example.carparkinglot.domain.exception.DomainCode;
import com.example.carparkinglot.domain.exception.DomainException;
import com.example.carparkinglot.infrastructure.restful.RestfulApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<RestfulApiError> missingRequestParams(HttpServletRequest request,
                                                              MissingServletRequestParameterException ex) {
    log.error("missing request params for uri={}, ex={}", request.getRequestURI(), ex.getMessage());
    return ResponseEntity.badRequest()
        .body(RestfulApiError.builder()
            .errorId(DomainCode.MISSING_PARAM.getValue())
            .message("Missing request parameters")
            .build());
  }

}
