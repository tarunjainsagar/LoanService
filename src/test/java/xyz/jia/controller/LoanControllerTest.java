package xyz.jia.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import xyz.jia.exception.InvalidRequestException;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.model.input.InstallmentProjectionInput;
import xyz.jia.model.response.ApiResponse;
import xyz.jia.repository.QueryLogRepository;
import xyz.jia.service.CalculatorFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanControllerTest {

    @InjectMocks
    private LoanController loanController;

    @Mock
    private QueryLogRepository queryLogRepository;

    @Mock
    private CalculatorFactory calculatorFactory;

    @Test
    public void testFeeProjections() {
        FeeProjectionInput feeProjectionInput = new FeeProjectionInput();
        ApiResponse expectedResponse = new ApiResponse();
        expectedResponse = expectedResponse.prepareSuccessResponse(null);

        ApiResponse response = loanController.feeProjections(feeProjectionInput);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testInstallmentProjections() {
        InstallmentProjectionInput installmentProjectionInput = new InstallmentProjectionInput();
        ApiResponse expectedResponse = new ApiResponse();
        expectedResponse = expectedResponse.prepareSuccessResponse(null);

        ApiResponse response = loanController.installmentProjections(installmentProjectionInput);

        assertEquals(expectedResponse, response);
    }

    @Test
    public void testQueryHistory() {
        Date fromDate = new Date();
        Date toDate = new Date();
        List<QueryLog> expectedQueryLogs = new ArrayList<>();

        when(queryLogRepository.findByRequestTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class), any()))
                .thenReturn(expectedQueryLogs);

        List<QueryLog> queryLogs = loanController.queryHistory(fromDate, toDate);

        assertEquals(expectedQueryLogs, queryLogs);
    }

    @Test
    public void testQueryHistoryNoDateRange() {
        Date fromDate = null;
        Date toDate = null;

        Page<QueryLog> expectedQueryLogsPage = new PageImpl<>(new ArrayList<>());

        when(queryLogRepository.findAll(any(PageRequest.class))).thenReturn(expectedQueryLogsPage);
        List<QueryLog> expectedQueryLogs = expectedQueryLogsPage.getContent();

        List<QueryLog> queryLogs = loanController.queryHistory(fromDate, toDate);

        verify(queryLogRepository).findAll(any(PageRequest.class));

        assertEquals(expectedQueryLogs, queryLogs);
    }

    @Test(expected = InvalidRequestException.class)
    public void testQueryHistoryException() {
        Date fromDate = new Date();
        Date toDate = new Date();

        when(queryLogRepository.findByRequestTimestampBetween(any(LocalDateTime.class), any(LocalDateTime.class), any()))
                .thenThrow(new RuntimeException("Database error"));

        loanController.queryHistory(fromDate, toDate);
    }
}
