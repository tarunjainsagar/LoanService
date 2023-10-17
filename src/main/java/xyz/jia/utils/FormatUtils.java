package xyz.jia.utils;

import xyz.jia.model.response.AbstractOutput;
import xyz.jia.model.response.ProjectionReponse;

public class FormatUtils {

    public static String formatResponse(AbstractOutput abstractOutput) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProjectionReponse response : abstractOutput.getProjectionResponseList()) {
            // Append each date and amount in the specified format
            stringBuilder.append(response.getDate()).append(" => ").append(response.getAmount()).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
