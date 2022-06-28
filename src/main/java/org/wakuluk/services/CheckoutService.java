package org.wakuluk.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;
import org.wakuluk.exception.InvalidDateFormatException;
import org.wakuluk.exception.ToolCodeNotFoundException;
import org.wakuluk.model.internal.Tool;
import org.wakuluk.model.internal.ToolType;
import org.wakuluk.model.response.RentalAgreement;
import org.wakuluk.services.util.CalendarUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutService {
  private final ToolsService toolsService;
  private SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");

  public RentalAgreement getRentalAgreement(
      String toolCode, int rentalDays, int discountPercent, String dateString)
      throws ToolCodeNotFoundException, InvalidDateFormatException {
    Calendar calendar = Calendar.getInstance();
    Date checkoutDate;
    Date returnDate;
    try {
      checkoutDate = formatter.parse(dateString);
    } catch (ParseException e) {
      String message =
          String.format(
              "Bad request - date %s could not be parsed "
                  + "because it is in the wrong format. "
                  + "Should be in the format M/d/yy",
              dateString);
      log.error(message);
      throw new InvalidDateFormatException(e, message);
    }
    calendar.setTime(checkoutDate);
    calendar.add(Calendar.DATE, rentalDays);
    returnDate = calendar.getTime();
    Tool tool = toolsService.getTool(toolCode);
    ToolType toolType = tool.getToolType();
    int chargeDays = getChargeableDays(checkoutDate, returnDate, rentalDays, toolType);

    // Finds the cost prior to the applied discount
    double preDiscountCharge = chargeDays * toolType.getDailyCharge();
    double roundPreDiscountCharge = Precision.round(preDiscountCharge, 2);

    // Finds the cost prior to the applied discount
    double discountAmount = roundPreDiscountCharge * (discountPercent / 100.00);
    double roundDiscountAmount = Precision.round(discountAmount, 2);

    double finalCharge = roundPreDiscountCharge - (roundDiscountAmount);

    Locale usa = new Locale("en", "US");
    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

    RentalAgreement rentalAgreement =
        RentalAgreement.builder()
            .toolCode(tool.getToolCode())
            .toolBrand(tool.getBrand())
            .toolType(toolType.getType())
            .rentalDays(rentalDays)
            .checkoutDate(formatter.format(checkoutDate))
            .dueDate(formatter.format(returnDate))
            .dailyRentalCharge(dollarFormat.format(tool.getToolType().getDailyCharge()))
            .chargeDays(chargeDays)
            .preDiscountCharge(dollarFormat.format(roundPreDiscountCharge))
            .discountPercent(new StringBuilder().append(discountPercent).append("%").toString())
            .discountAmount(dollarFormat.format(roundDiscountAmount))
            .finalCharge(dollarFormat.format(finalCharge))
            .build();

    return rentalAgreement;
  }

  private int getChargeableDays(
      Date checkoutDate, Date returnDate, int daysRented, ToolType toolType) {
    int chargedDays = daysRented;

    Calendar startCalendar = Calendar.getInstance();
    startCalendar.setTime(checkoutDate);
    Date startDate = startCalendar.getTime();

    if (!toolType.isHolidayCharge()) {
      int startYear = startCalendar.get(Calendar.YEAR);

      // find the end date range -- the range does not include the end date
      Calendar endCalendar = Calendar.getInstance();
      endCalendar.setTime(returnDate);
      endCalendar.add(Calendar.DATE, 1);
      Date endDate = endCalendar.getTime();
      int endYear = endCalendar.get(Calendar.YEAR);

      int holidays = 0;
      // We iterate over the years the range transpires (including the startYear)
      for (int i = startYear; i <= endYear; i++) {
        Date laborDay = CalendarUtil.getLaborDay(i);
        // if laborDay is within the range, add one to the holidays int
        holidays += CalendarUtil.isDateInRange(startDate, laborDay, endDate) ? 1 : 0;

        Date independenceDay = CalendarUtil.getIndependenceDay(i);
        // if independenceDay is within the range, add one to the holidays int
        holidays += CalendarUtil.isDateInRange(startDate, independenceDay, endDate) ? 1 : 0;
      }
      chargedDays -= holidays;
    }

    if (!toolType.isWeekendCharge()) {
      // Chargeable days do not start until the day after checkout
      // CalendarUtil.numberOfWeekdays takes into account the initial day of week when
      // calculating the number of weekend days within a range of days.
      startCalendar.add(Calendar.DATE, 1);
      int weekendDays = CalendarUtil.numberOfWeekendDays(startCalendar, daysRented);
      chargedDays -= weekendDays;
    }

    return chargedDays;
  }
}
