package xyz.jia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.repository.QueryLogRepository;
import xyz.jia.service.CalculatorFactory;

import javax.validation.Valid;
import java.util.List;

import static xyz.jia.constants.UriConstants.*;

@RestController
@RequestMapping(baseLoanApi)
public class LoanController {

    @Autowired
    private QueryLogRepository queryLogRepository;

    @PostMapping(feeProjectionApi)
    public String feeProjections(@RequestBody @Valid FeeProjectionInput feeProjectionInput) {
        return CalculatorFactory.calculate(feeProjectionApi, feeProjectionInput);
    }

    @PostMapping(installmentProjectionApi)
    public String installmentProjections(@RequestBody InstallmentProjectionInput installmentProjectionInput) {
        return CalculatorFactory.calculate(installmentProjectionApi, installmentProjectionInput);
    }

    @GetMapping(queryHistoryApi)
    public List<QueryLog> queryHistory() {
        return queryLogRepository.findAll();
    }
}
