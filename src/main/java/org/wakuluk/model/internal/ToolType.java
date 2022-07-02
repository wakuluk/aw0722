package org.wakuluk.model.internal;

import lombok.Data;

@Data
public class ToolType {
  private String type;
  private double dailyCharge;
  private boolean weekdayCharge;
  private boolean weekendCharge;
  private boolean holidayCharge;
}
