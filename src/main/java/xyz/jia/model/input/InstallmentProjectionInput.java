package xyz.jia.model.input;

import lombok.Data;

@Data
public class InstallmentProjectionInput extends AbstractInput {

    @Override
    public String toString() {
        return "InstallmentProjectionInput{".concat(super.toString()).concat("}");
    }
}

