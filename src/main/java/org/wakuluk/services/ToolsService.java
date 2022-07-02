package org.wakuluk.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.wakuluk.ToolsMapConfiguration;
import org.wakuluk.exception.ToolCodeNotFoundException;
import org.wakuluk.model.internal.Tool;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToolsService {

  // Ideally, the tools data would be in a repo and the toolMap
  // would be populated from it.
  private final ToolsMapConfiguration toolsMapConfiguration;

  public Tool getTool(String toolCode) throws ToolCodeNotFoundException {
    // Normalize to uppercase
    String upperToolCode = toolCode.toUpperCase();
    Tool tool = toolsMapConfiguration.getMap().get(upperToolCode);
    if (tool == null) {
      String errorMessage =
          String.format("ToolCodeNotFoundException: Tool Code %s not found", upperToolCode);
      log.error(errorMessage);
      throw new ToolCodeNotFoundException(errorMessage);
    }
    return tool;
  }
}
