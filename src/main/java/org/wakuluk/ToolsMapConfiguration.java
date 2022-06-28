package org.wakuluk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.wakuluk.model.internal.Tool;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tools")
@Data
public class ToolsMapConfiguration {
  private Map<String, Tool> map;
}
