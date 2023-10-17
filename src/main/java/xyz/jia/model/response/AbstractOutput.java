package xyz.jia.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
/*
 * This class is created keeping in mind that the output response for both api in the requirement have same format.
 * This file can be removed or updated in future based on requirement.
 * */
public abstract class AbstractOutput {

    List<ProjectionReponse> projectionResponseList;

    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse response", e);
            // todo: handle in a better way
            e.printStackTrace();
            return "{}";
        }
    }
}
