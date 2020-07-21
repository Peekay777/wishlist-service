package com.koutsios.wishlistservice.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class WishlistAdvice {

  @ResponseBody
  @ExceptionHandler(WishlistNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public String productNotFoundHandler(WishlistNotFoundException exception) {
    log.error(exception.getMessage());
    return exception.getMessage();
  }

}
