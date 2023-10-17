package xyz.jia.model.input;

import lombok.Data;

@Data
public class FeeProjectionInput extends AbstractInput {
    @Override
    public String toString() {
        return "FeeProjectionInput{".concat(super.toString()).concat("}");
    }
}

