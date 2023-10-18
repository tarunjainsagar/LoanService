package xyz.jia.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import xyz.jia.constants.FormatConstants;
import xyz.jia.exception.InvalidRequestException;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.model.response.ApiResponse;
import xyz.jia.repository.QueryLogRepository;
import xyz.jia.service.CalculatorFactory;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
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
    public ApiResponse installmentProjections(@RequestBody @Valid InstallmentProjectionInput installmentProjectionInput) {
        ApiResponse response = new ApiResponse();
        ObjectNode installmentProjections = CalculatorFactory.calculate(installmentProjectionApi, installmentProjectionInput);
        return response.prepareSuccessResponse(installmentProjections);
    }

    @GetMapping(queryHistoryApi)
    public List<QueryLog> queryHistory(
            @RequestParam(required = false) @DateTimeFormat(pattern = FormatConstants.dateFormat) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = FormatConstants.dateFormat) Date toDate) {
        try {
            if (fromDate != null && toDate != null) {
                // Return data between fromDate and toDate
                LocalDateTime fromDateTime = fromDate.toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().atTime(LocalTime.MIN);
                LocalDateTime toDateTime = toDate.toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().atTime(LocalTime.MAX);
                return queryLogRepository.findByRequestTimestampBetween(fromDateTime, toDateTime, Sort.by(Sort.Order.desc("requestTimestamp")));
            } else {
                // Return the 50 latest records
                PageRequest pageRequest = PageRequest.of(0, 50, Sort.by(Sort.Order.desc("requestTimestamp")));
                return queryLogRepository.findAll(pageRequest).getContent();
            }
        } catch (Exception e) {
            // todo: fix: add more detailed error handling
            throw new InvalidRequestException(e.getMessage());
        }
    }

}
