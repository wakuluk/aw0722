package org.wakuluk.services.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtilTest {

  private SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");

  @Test
  public void dateInRangeTest() throws ParseException {

    Date startDate = formatter.parse("7/3/22");
    Date date = formatter.parse("7/4/22");
    Date endDate = formatter.parse("7/5/22");

    boolean dateInRange = CalendarUtil.isDateInRange(startDate, date, endDate);

    Assertions.assertThat(dateInRange).isTrue();
  }

  @Test
  public void dateInRange_after_Test() throws ParseException {

    Date startDate = formatter.parse("07/3/22");
    Date date = formatter.parse("07/6/22");
    Date endDate = formatter.parse("07/5/22");

    boolean dateInRange = CalendarUtil.isDateInRange(startDate, date, endDate);

    Assertions.assertThat(dateInRange).isFalse();
  }

  @Test
  public void dateInRange_before_Test() throws ParseException {

    Date startDate = formatter.parse("7/3/22");
    Date date = formatter.parse("7/2/22");
    Date endDate = formatter.parse("7/5/22");

    boolean dateInRange = CalendarUtil.isDateInRange(startDate, date, endDate);

    Assertions.assertThat(dateInRange).isFalse();
  }

  @Test
  public void dateInRange_onStart_Test() throws ParseException {

    Date startDate = formatter.parse("7/3/22");
    Date date = formatter.parse("7/3/22");
    Date endDate = formatter.parse("7/5/22");

    boolean dateInRange = CalendarUtil.isDateInRange(startDate, date, endDate);

    Assertions.assertThat(dateInRange).isFalse();
  }

  @Test
  public void dateInRange_onEnd_Test() throws ParseException {

    Date startDate = formatter.parse("7/3/22");
    Date date = formatter.parse("7/5/22");
    Date endDate = formatter.parse("7/5/22");

    boolean dateInRange = CalendarUtil.isDateInRange(startDate, date, endDate);

    Assertions.assertThat(dateInRange).isFalse();
  }

  @Test
  public void getFourth2022() throws ParseException {
    // July 4th falls on a monday in 2022.
    // The celebration date will be on July 4th.
    Date date = CalendarUtil.getIndependenceDay(2022);

    String actual = formatter.format(date);

    String expected = "7/4/22";
    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void getFourth2021() throws ParseException {
    // July 4th falls on a Sunday in 2021.
    // The celebration date will be on July 5th.
    Date date = CalendarUtil.getIndependenceDay(2021);

    String actual = formatter.format(date);

    String expected = "7/5/21";
    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void getFourth2020() throws ParseException {
    // July 4th falls on a Saturday in 2020.
    // The celebration date will be on July 3rd.
    Date date = CalendarUtil.getIndependenceDay(2020);

    String actual = formatter.format(date);

    String expected = "7/3/20";
    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void getLaborDay2022() throws ParseException {
    Date date = CalendarUtil.getLaborDay(2022);
    String actual = formatter.format(date);

    String expected = "9/5/22";
    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void getLaborDay2023() throws ParseException {
    Date date = CalendarUtil.getLaborDay(2023);
    String actual = formatter.format(date);

    String expected = "9/4/23";
    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_sundayToFriday() {
    // Start on a Sunday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JUNE, 26);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 6);

    int expected = 1;

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_SundayToSaturday() {
    // Start on Monday and go to Friday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JUNE, 26);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 7);

    int expected = 2;

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_MondayToFriday() {
    // Start on Monday and go to Friday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JUNE, 27);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 5);

    int expected = 0;

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_MondayToSaturday() {
    // Start on Monday and go to Friday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JUNE, 27);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 6);

    int expected = 1;

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_Sunday_twoWeeks() {
    // Start on Monday and go to Friday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JUNE, 26);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 14);

    int expected = 4;

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_Wednesday_twoWeeks() {
    // Start on Monday and go to Friday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JUNE, 29);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 14);

    int expected = 4;

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_Saturday() {
    // Start on Monday and go to Friday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JULY, 2);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 1);

    int expected = 1;

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testNumOfWeekendDays_aLotOfDays() {
    // Start on Monday and go to Friday
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.JULY, 2);
    calendar.getTime();
    int actual = CalendarUtil.numberOfWeekendDays(calendar, 3389);

    int expected = findWeekedHelper(calendar, 3389);

    Assertions.assertThat(actual).isEqualTo(expected);
  }

  private int findWeekedHelper(Calendar calendar, int rangeOfDays) {
    int weekendDays = 0;
    for (int i = 0; i < rangeOfDays; i++) {
      int dayOfweek = calendar.get(Calendar.DAY_OF_WEEK);
      if (dayOfweek == 1 || dayOfweek == 7) {
        weekendDays++;
      }
      calendar.add(Calendar.DATE, 1);
    }
    return weekendDays;
  }
}
