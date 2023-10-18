package xyz.jia.model.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import xyz.jia.constants.UriConstants;

@Slf4j
public class InstallmentProjectionResponse extends AbstractOutput {
    @Override
    public ObjectNode buildOutput(boolean showDetails) {
        ObjectNode response = super.buildOutput(showDetails);
        if (showDetails) {
            response.put("projections_type", UriConstants.installmentProjectionApi);
        }
        return response;
    }
}
