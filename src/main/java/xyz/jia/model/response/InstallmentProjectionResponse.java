package xyz.jia.model.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import xyz.jia.constants.UrlConstants;

@Slf4j
public class InstallmentProjectionResponse extends AbstractOutput {
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String superToString = super.toString();
            ObjectNode installmentResponse = objectMapper.createObjectNode();
            installmentResponse.put("type", UrlConstants.installmentProjectionApi);
            installmentResponse.put("data", superToString);
            return installmentResponse.toString();
        } catch (Exception e) {
            log.error("Failed to parse response: installmentProjectionResponse: ", e);
            // todo: handle in a better way
            e.printStackTrace();
            return "{}";
        }
    }
}
