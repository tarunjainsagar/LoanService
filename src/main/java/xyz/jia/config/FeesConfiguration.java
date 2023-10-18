package xyz.jia.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("loan.fees")
@Data
public class FeesConfiguration {
    private FeeParameters weekly;
    private FeeParameters monthly;
}

