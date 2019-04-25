package com.recoveryrecord.surveyandroid.validation;

import com.recoveryrecord.surveyandroid.question.Validation;

import java.util.List;
import java.util.Map;

public class DefaultValidator implements Validator {
    private FailedValidationListener mListener;

    public DefaultValidator(FailedValidationListener failedValidationListener) {
        mListener = failedValidationListener;
    }

    @Override
    public ValidationResult validate(List<Validation> validations, String answer, AnswerProvider answerProvider) {
        if (validations == null || validations.isEmpty()) {
            return ValidationResult.success();
        }
        for (Validation validation : validations) {
            if (!isConditionMet(validation, answer, answerProvider)) {
                return new ValidationResult(false, validation.onFailMessage);
            }
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationResult validate(List<Validation> validations, Map<String, String> answers, AnswerProvider answerProvider) {
        if (validations == null || validations.isEmpty()) {
            return ValidationResult.success();
        }
        for (Validation validation : validations) {
            String answer = answers.get(validation.forLabel);
            if (answer == null) {
                continue;
            }
            if (!isConditionMet(validation, answer, answerProvider)) {
                return new ValidationResult(false, validation.onFailMessage);
            }
        }
        return ValidationResult.success();
    }

    private boolean isConditionMet(Validation validation, String answer, AnswerProvider answerProvider) {
        Double value = validation.value;
        if (validation.answerToQuestionId != null) {
            if (answerProvider == null) {
                throw new IllegalStateException("Validation requires a non-null AnswerProvider");
            }
            value = Double.parseDouble(answerProvider.answerFor(validation.answerToQuestionId).getValue());
        }
        Double numAnswer = Double.parseDouble(answer);
        switch (validation.operation) {
            case "equals":
                return numAnswer.equals(value);
            case "not equals":
                return !numAnswer.equals(value);
            case "greater than":
                return numAnswer.compareTo(value) > 0;
            case "greater than or equal to":
                return numAnswer.compareTo(value) >= 0;
            case "less than":
                return numAnswer.compareTo(value) < 0;
            case "less than or equal to":
                return numAnswer.compareTo(value) <= 0;
        }
        return false;
    }

    @Override
    public void validationFailed(String message) {
        if (mListener != null) {
            mListener.validationFailed(message);
        }
    }
}
