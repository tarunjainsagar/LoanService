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

    @Autowired
    private QueryLogRepository queryLogRepository;

    public void logRequestDetails(String baseUri, String requestUri, AbstractInput requestInputParams) {
        QueryLog history = new QueryLog();
        history.setRequestUri(getUri(baseUri, requestUri));
        // todo: fix: recording one parameter in one column would be a good practice to search efficiently as a later stage
        history.setRequestParams(requestInputParams.toString());
        history.setRequestTimestamp(LocalDateTime.now());

        queryLogRepository.save(history);
    }

    private String getUri(String baseUri, String requestUri) {
        return baseUri.concat(requestUri);
    }
}
