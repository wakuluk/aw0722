package org.wakuluk.services.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

  /**
   * Returns boolean of whether date is between startDate and endDate
   *
   * @param startDate date of day before valid range
   * @param date date to be checked
   * @param endDate date of day after valid range
   * @return
   */
  public static boolean isDateInRange(Date startDate, Date date, Date endDate) {
    boolean dateInRange = false;
    if (date.before(endDate) && date.after(startDate)) {
      dateInRange = true;
    }
    return dateInRange;
  }

  /**
   * Given a year, this method returns the Date the of the first monday in September
   *
   * @param year
   * @return
   */
  public static Date getLaborDay(int year) {
    Calendar laborDayCalendar = Calendar.getInstance();
    // Find the first monday in September
    laborDayCalendar.set(Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
    laborDayCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
    laborDayCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
    laborDayCalendar.set(Calendar.YEAR, year);
    Date laborDay = laborDayCalendar.getTime();
    return laborDay;
  }

  /**
   * Given a year, this method returns the day Independence Day is celebrated
   *
   * @param year
   * @return
   */
  public static Date getIndependenceDay(int year) {
    Calendar independenceDayCalendar = Calendar.getInstance();
    // Set Calendar to independence day of specific year
    independenceDayCalendar.set(year, Calendar.JULY, 4);
    // and get the day of the week it's on.
    int dayOfWeek = independenceDayCalendar.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == 7) {
      // if the day of the week is on a Saturday, then set the holiday to the day before.
      independenceDayCalendar.add(Calendar.DATE, -1);
    } else if (dayOfWeek == 1) {
      // if the day of the week is on a Sunday, then set the holiday to the day after.
      independenceDayCalendar.add(Calendar.DATE, 1);
    }

    return independenceDayCalendar.getTime();
  }

  /**
   * Given a calendar set to a specific, this method will return the number of weekend days within a
   * specified range of days.
   *
   * <p>The result will have the start day as including within the range of days.
   *
   * @param calendar calendar set to day to start calculating from
   * @param rangeOfDays specified range of days
   * @return
   */
  public static int numberOfWeekendDays(Calendar calendar, int rangeOfDays) {
    // startWeekday will be between 1-7, with 1 = Sunday and 7 = Saturday
    int startWeekday = calendar.get(Calendar.DAY_OF_WEEK);

    // daysRented includes the weekday we start on. So, we have to take one day away
    // from days rented in order to find the endWeekday.
    int totalDays = (rangeOfDays - 1) + startWeekday;
    // by moding total days by 7, we find the weekday the tool is returned
    int endWeekday = totalDays % 7;

    // By dividing by 7 and flooring the result, we find the number of weeks spanned from the
    // startWeekday
    int weeks = Math.floorDiv(totalDays, 7);

    // There are 2 weekend days per week, so multiplying by 2 will return the number weekend days
    // (without counting the final week)
    int numWeekendDays = weeks * 2;
    // For the first week, unless the first day is Sunday, then only weekday found will be Saturday
    if (startWeekday != 1) {
      numWeekendDays -= 1;
    }
    // For the end week, unless we end on Saturday, then the only weekday passed will be Sunday
    if (endWeekday >= 1) {
      numWeekendDays += 1;
    }
    // The above two if statements also account for only spanning within one week

    return numWeekendDays;
  }
}
