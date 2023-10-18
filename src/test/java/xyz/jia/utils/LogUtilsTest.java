package xyz.jia.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import xyz.jia.model.input.FeeProjectionInput;
import xyz.jia.repository.QueryLogRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogUtilsTest {

    @InjectMocks
    private LogUtils logUtils;

    @Mock
    private QueryLogRepository queryLogRepository;

    @Test
    public void logRequestDetails() {
        String baseUri = "https://example.com/api/";
        String requestUri = "calculate";
        FeeProjectionInput requestInputParams = new FeeProjectionInput();
        requestInputParams.setAmount(1000);

        logUtils.logRequestDetails(baseUri, requestUri, requestInputParams);

        verify(queryLogRepository, times(1)).save(
                /* Keep any as timestamp changes on actual execution */ any());
    }

    @Test
    public void getUri() {
        String baseUri = "https://example.com/api/";
        String requestUri = "calculate";
        String fullUri = logUtils.getUri(baseUri, requestUri);
        assertEquals("https://example.com/api/calculate", fullUri);
    }

}