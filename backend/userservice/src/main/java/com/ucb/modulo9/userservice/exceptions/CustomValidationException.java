package com.ucb.modulo9.userservice.exceptions;

import java.util.List;
import java.util.Map;

public class CustomValidationException extends RuntimeException {

    private final Map<String, List<String>> errors;

    public CustomValidationException(Map<String, List<String>> errors) {
        super("Error de validacion");
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
