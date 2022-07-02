package org.wakuluk.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.wakuluk.exception.InvalidDateFormatException;
import org.wakuluk.exception.ToolCodeNotFoundException;
import org.wakuluk.model.response.RentalAgreement;
import org.wakuluk.services.CheckoutService;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping(value = "/rental")
@RequiredArgsConstructor
@Slf4j
public class RentalCheckoutController {
  private final CheckoutService checkoutService;

  @RequestMapping(value = "/checkout", method = RequestMethod.GET)
  public RentalAgreement checkout(
      @RequestParam @NotBlank(message = "cannot be blank") String toolCode,
      @RequestParam @Min(value = 1, message = "must be 1 or greater") int rentalDays,
      @RequestParam @NotBlank(message = "must not be blank") String date,
      @RequestParam
          @Min(value = 0, message = "must be between 0 and 100")
          @Max(value = 100, message = "must be between 0 and 100")
          int discount)
      throws ToolCodeNotFoundException, InvalidDateFormatException {

    RentalAgreement checkout =
        checkoutService.getRentalAgreement(toolCode, rentalDays, discount, date);
    logOutput(checkout);
    return checkout;
  }

  public void logOutput(RentalAgreement rentalAgreement) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.valueToTree(rentalAgreement);
    String output = jsonNode.toPrettyString();
    output = output.replaceAll("\"|,|\\{|}", "");
    log.info(output);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    String message = String.format("Bad Request - %s \n", e.getMessage());
    log.error(message);
    return ResponseEntity.badRequest().body(message);
  }
}
