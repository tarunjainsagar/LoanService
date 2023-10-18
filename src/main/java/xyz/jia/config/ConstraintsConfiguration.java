package xyz.jia.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("loan.constraints")
@Data
public class ConstraintsConfiguration {
    private int minAmount;
    private int minWeeklyDuration;
    private int maxWeeklyDuration;
    private int minMonthlyDuration;
    private int maxMonthlyDuration;
    private int daysInWeek;
    private int daysInMonth;
}
