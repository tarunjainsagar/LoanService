package xyz.jia.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.model.response.ApiResponse;
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
    public ApiResponse feeProjections(@RequestBody @Valid FeeProjectionInput feeProjectionInput) {
        ApiResponse response = new ApiResponse();
        ObjectNode feeProjections = CalculatorFactory.calculate(feeProjectionApi, feeProjectionInput);
        return response.prepareSuccessResponse(feeProjections);
    }

    @PostMapping(installmentProjectionApi)
    public ApiResponse installmentProjections(@RequestBody InstallmentProjectionInput installmentProjectionInput) {
        ApiResponse response = new ApiResponse();
        ObjectNode installmentProjections = CalculatorFactory.calculate(installmentProjectionApi, installmentProjectionInput);
        return response.prepareSuccessResponse(installmentProjections);
    }

    @GetMapping(queryHistoryApi)
    public List<QueryLog> queryHistory() {
        return queryLogRepository.findAll();
    }
}
