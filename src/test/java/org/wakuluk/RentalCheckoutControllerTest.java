package org.wakuluk;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wakuluk.controller.RentalCheckoutController;
import org.wakuluk.exception.InvalidDateFormatException;
import org.wakuluk.exception.ToolCodeNotFoundException;
import org.wakuluk.model.response.RentalAgreement;

import javax.validation.ConstraintViolationException;

@SpringBootTest
public class RentalCheckoutControllerTest {

  @Autowired public RentalCheckoutController rentalCheckoutController;

  // Required tests
  @Test
  public void test1() throws InvalidDateFormatException, ToolCodeNotFoundException {
    try {
      rentalCheckoutController.checkout("JAKR", 5, "9/3/15", 101);
    } catch (ConstraintViolationException e) {
      Assertions.assertThat(e.getMessage())
          .isEqualTo("checkout.discount: must be between 0 and 100");
    }
  }

  @Test
  public void test2() throws InvalidDateFormatException, ToolCodeNotFoundException {
    RentalAgreement actual = rentalCheckoutController.checkout("LADW", 3, "7/2/20", 10);

    RentalAgreement expected =
        RentalAgreement.builder()
            .toolCode("LADW")
            .toolType("Ladder")
            .toolBrand("Werner")
            .rentalDays(3)
            .checkoutDate("7/2/20")
            .dueDate("7/5/20")
            .dailyRentalCharge("$1.99")
            .chargeDays(2)
            .preDiscountCharge("$3.98")
            .discountPercent("10%")
            .discountAmount("$0.40")
            .finalCharge("$3.58")
            .build();

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void test3() throws InvalidDateFormatException, ToolCodeNotFoundException {
    RentalAgreement actual = rentalCheckoutController.checkout("CHNS", 5, "7/2/15", 25);

    RentalAgreement expected =
        RentalAgreement.builder()
            .toolCode("CHNS")
            .toolType("Chainsaw")
            .toolBrand("Stihl")
            .rentalDays(5)
            .checkoutDate("7/2/15")
            .dueDate("7/7/15")
            .dailyRentalCharge("$1.49")
            .chargeDays(3)
            .preDiscountCharge("$4.47")
            .discountPercent("25%")
            .discountAmount("$1.12")
            .finalCharge("$3.35")
            .build();

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void test4() throws InvalidDateFormatException, ToolCodeNotFoundException {
    RentalAgreement actual = rentalCheckoutController.checkout("JAKD", 6, "9/3/15", 0);

    RentalAgreement expected =
        RentalAgreement.builder()
            .toolCode("JAKD")
            .toolType("Jackhammer")
            .toolBrand("DeWalt")
            .rentalDays(6)
            .checkoutDate("9/3/15")
            .dueDate("9/9/15")
            .dailyRentalCharge("$2.99")
            .chargeDays(3)
            .preDiscountCharge("$8.97")
            .discountPercent("0%")
            .discountAmount("$0.00")
            .finalCharge("$8.97")
            .build();

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void test5() throws InvalidDateFormatException, ToolCodeNotFoundException {
    RentalAgreement actual = rentalCheckoutController.checkout("JAKR", 9, "7/2/15", 0);

    RentalAgreement expected =
        RentalAgreement.builder()
            .toolCode("JAKR")
            .toolType("Jackhammer")
            .toolBrand("Ridgid")
            .rentalDays(9)
            .checkoutDate("7/2/15")
            .dueDate("7/11/15")
            .dailyRentalCharge("$2.99")
            .chargeDays(5)
            .preDiscountCharge("$14.95")
            .discountPercent("0%")
            .discountAmount("$0.00")
            .finalCharge("$14.95")
            .build();

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  //Exception Tests
  @Test
  public void test6() throws InvalidDateFormatException, ToolCodeNotFoundException {
    RentalAgreement actual = rentalCheckoutController.checkout("JAKR", 4, "7/2/20", 50);

    RentalAgreement expected =
        RentalAgreement.builder()
            .toolCode("JAKR")
            .toolType("Jackhammer")
            .toolBrand("Ridgid")
            .rentalDays(4)
            .checkoutDate("7/2/20")
            .dueDate("7/6/20")
            .dailyRentalCharge("$2.99")
            .chargeDays(1)
            .preDiscountCharge("$2.99")
            .discountPercent("50%")
            .discountAmount("$1.50")
            .finalCharge("$1.49")
            .build();

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void badRentalDay() throws InvalidDateFormatException, ToolCodeNotFoundException {
    try {
      rentalCheckoutController.checkout("JAKR", 0, "9/3/15", 1);
    } catch (ConstraintViolationException e) {
      Assertions.assertThat(e.getMessage()).isEqualTo("checkout.rentalDays: must be 1 or greater");
    }
  }

  @Test
  public void badToolCode() throws InvalidDateFormatException {
    try {
      rentalCheckoutController.checkout("NOT_THERE", 1, "9/3/15", 1);
    } catch (ToolCodeNotFoundException e) {
      Assertions.assertThat(e.getMessage())
          .isEqualTo("ToolCodeNotFoundException: Tool Code NOT_THERE not found");
    }
  }

  @Test
  public void badDate() throws ToolCodeNotFoundException {
    try {
      rentalCheckoutController.checkout("JAKR", 1, "BADDATE", 1);
    } catch (InvalidDateFormatException e) {
      Assertions.assertThat(e.getMessage())
          .isEqualTo(
              "Bad request - date BADDATE could not be "
                  + "parsed because it is in the wrong format. Should be in the format M/d/yy");
    }
  }
}
