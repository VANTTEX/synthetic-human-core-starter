package ru.T1Debut.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "weyland.audit")
public class WeylandAuditProperties {
    private boolean consoleOutput = true;
    private String kafkaTopic = "";
}
