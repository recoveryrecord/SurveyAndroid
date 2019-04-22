package com.recoveryrecord.surveyandroid.validation;

import com.recoveryrecord.surveyandroid.question.Validation;

import java.util.List;
import java.util.Map;

public interface Validator {
    ValidationResult validate(List<Validation> validations, String answer);
    ValidationResult validate(List<Validation> validations, Map<String, String> answer);
    void validationFailed(String message);
}
