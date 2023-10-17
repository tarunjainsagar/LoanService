package xyz.jia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.InputInterface;
import xyz.jia.repository.QueryLogRepository;

import java.time.LocalDateTime;

@Component
public class LogUtils {
    @Autowired
    private QueryLogRepository queryLogRepository;

    public void logRequestDetails(String baseUri, String requestUri, InputInterface requestInputParams) {
        QueryLog history = new QueryLog();
        history.setRequestUri(getUri(baseUri, requestUri));
        history.setRequestParams(requestInputParams.toString());
        history.setRequestTimestamp(LocalDateTime.now());

        queryLogRepository.save(history);
    }

    private String getUri(String baseUri, String requestUri) {
        return baseUri.concat(requestUri);
    }
}
