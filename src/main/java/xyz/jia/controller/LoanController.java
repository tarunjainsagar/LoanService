package xyz.jia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.model.response.FeeProjectionResponse;
import xyz.jia.model.response.InstallmentProjectionResponse;
import xyz.jia.repository.QueryLogRepository;
import xyz.jia.service.FeeProjectionCalculator;
import xyz.jia.service.InstallmentProjectionCalculator;
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
    public List<FeeProjectionResponse> postFeeProjections(@RequestBody FeeProjectionInput feeProjectionInput) {
        logUtils.logRequestDetails(baseLoanApi, feeProjectionApi, feeProjectionInput);
        return FeeProjectionCalculator.calculateFeeProjections(feeProjectionInput.getAmount(), feeProjectionInput.getDuration(), feeProjectionInput.getStartDate());
    }

    @PostMapping(installmentProjectionApi)
    public List<InstallmentProjectionResponse> postInstallmentProjections(@RequestBody InstallmentProjectionInput installmentProjectionInput) {
        logUtils.logRequestDetails(baseLoanApi, installmentProjectionApi, installmentProjectionInput);
        return InstallmentProjectionCalculator.calculateInstallmentProjections(installmentProjectionInput.getAmount(), installmentProjectionInput.getDuration(), installmentProjectionInput.getStartDate());
    }

    @GetMapping(queryHistory)
    public List<QueryLog> getAllQueries() {
        return queryLogRepository.findAll();
    }
}
