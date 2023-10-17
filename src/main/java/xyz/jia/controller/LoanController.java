package xyz.jia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.repository.QueryLogRepository;
import xyz.jia.service.CalculatorFactory;
import xyz.jia.utils.LogUtils;

import java.util.List;

import static xyz.jia.constants.UrlConstants.*;

@RestController
@RequestMapping(baseLoanApi)
public class LoanController {

    @Autowired
    private QueryLogRepository queryLogRepository;

    @Autowired
    private LogUtils logUtils;

    @PostMapping(feeProjectionApi)
    public String postFeeProjections(@RequestBody FeeProjectionInput feeProjectionInput) {
        logUtils.logRequestDetails(baseLoanApi, feeProjectionApi, feeProjectionInput);
        return CalculatorFactory.calculate(feeProjectionApi, feeProjectionInput);
    }

    @PostMapping(installmentProjectionApi)
    public String postInstallmentProjections(@RequestBody InstallmentProjectionInput installmentProjectionInput) {
        logUtils.logRequestDetails(baseLoanApi, installmentProjectionApi, installmentProjectionInput);
        return CalculatorFactory.calculate(installmentProjectionApi, installmentProjectionInput);
    }

    @GetMapping(queryHistory)
    public List<QueryLog> getAllQueries() {
        return queryLogRepository.findAll();
    }
}
