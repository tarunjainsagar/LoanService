package xyz.jia.controller;

import org.springframework.web.bind.annotation.*;
import xyz.jia.model.FeeProjection;
import xyz.jia.model.InstallmentProjection;
import xyz.jia.service.FeeProjectionCalculator;
import xyz.jia.service.InstallmentProjectionCalculator;

import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @GetMapping("/test")
    public String getFeeProjections(@RequestParam int amount) {
        return "Hello " + amount;
    }

    @GetMapping("/fee-projections")
    public List<FeeProjection> getFeeProjections(@RequestParam int amount, @RequestParam int duration, @RequestParam String startDate) {
        return FeeProjectionCalculator.calculateFeeProjections(amount, duration, startDate);
    }

    @GetMapping("/installment-projections")
    public List<InstallmentProjection> getInstallmentProjections(@RequestParam int amount, @RequestParam int duration, @RequestParam String startDate) {
        return InstallmentProjectionCalculator.calculateInstallmentProjections(amount, duration, startDate);
    }
}
