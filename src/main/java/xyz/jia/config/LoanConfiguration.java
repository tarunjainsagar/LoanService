package xyz.jia.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("loan")
@Data
public class LoanConfiguration {
    private ConstraintsConfiguration constraints;
    private FeesConfiguration fees;
}

