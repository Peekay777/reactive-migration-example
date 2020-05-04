package com.koutsios.reactivemigrationexample.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ProductAdvice {

  @ResponseBody
  @ExceptionHandler(ProductNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String productNotFoundHandler(ProductNotFoundException exception) {
    log.error(exception.getMessage(), exception);
    return exception.getMessage();
  }

}
