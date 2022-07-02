package org.wakuluk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDateFormatException extends Exception {
  public InvalidDateFormatException(ParseException e, String message) {
    super(message, e);
  }
}
