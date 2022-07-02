package org.wakuluk.model.internal;

import lombok.Data;

@Data
public class Tool {
  private String toolCode;
  private String brand;
  private ToolType toolType;
}
