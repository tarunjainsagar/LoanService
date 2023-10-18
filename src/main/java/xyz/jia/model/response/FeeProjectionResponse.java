package xyz.jia.model.response;

import lombok.extern.slf4j.Slf4j;
import xyz.jia.constants.UriConstants;

@Slf4j
public class FeeProjectionResponse extends AbstractOutput {

    @Override
    public StringBuilder buildOutput(boolean showDetails) {
        StringBuilder sb = super.buildOutput(showDetails);
        if (showDetails) {
            sb.append("projections_type=").append(UriConstants.feeProjectionApi).append(System.lineSeparator());
        }
        return sb;
    }
}
