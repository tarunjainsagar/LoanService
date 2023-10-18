package xyz.jia.model.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import xyz.jia.constants.ErrorConstants;

@Data
public class ApiResponse {

    private String status;
    private String message;
    private ObjectNode data;

    public ApiResponse prepareSuccessResponse(ObjectNode data) {
        this.setStatus(ErrorConstants.SUCCESS);
        this.setMessage(ErrorConstants.SUCCESS_MESSAGE);
        this.setData(data);
        return this;
    }
}

