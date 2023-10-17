package xyz.jia.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jia.model.entity.QueryLog;
import xyz.jia.model.input.AbstractInput;
import xyz.jia.repository.QueryLogRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LogUtils {

    private static QueryLogRepository queryLogRepository;
    @Autowired
    LogUtils(QueryLogRepository queryLogRepository) {
        LogUtils.queryLogRepository = queryLogRepository;
    }

    public static void logRequestDetails(String baseUri, String requestUri, AbstractInput requestInputParams) {
        QueryLog history = new QueryLog();
        history.setRequestUri(getUri(baseUri, requestUri));
        history.setRequestParams(requestInputParams.toString());
        history.setRequestTimestamp(LocalDateTime.now());

        queryLogRepository.save(history);
    }

    private static String getUri(String baseUri, String requestUri) {
        return baseUri.concat(requestUri);
    }
}
