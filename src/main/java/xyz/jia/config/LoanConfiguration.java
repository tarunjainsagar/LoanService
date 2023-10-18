package xyz.jia.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class LoanConfiguration {
    @Value("${loan.constraints.minAmount}")
    private int minAmount;

    @Value("${loan.constraints.minWeeklyDuration}")
    private int minWeeklyDuration;

    @Value("${loan.constraints.maxWeeklyDuration}")
    private int maxWeeklyDuration;

    @Value("${loan.constraints.minMonthlyDuration}")
    private int minMonthlyDuration;

    @Value("${loan.constraints.maxMonthlyDuration}")
    private int maxMonthlyDuration;
}

