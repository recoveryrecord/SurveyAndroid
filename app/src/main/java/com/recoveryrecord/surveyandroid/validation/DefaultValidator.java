package com.recoveryrecord.surveyandroid.validation;

import com.recoveryrecord.surveyandroid.question.Validation;

import java.util.List;
import java.util.Map;

public class DefaultValidator implements Validator {
    private static ValidationResult SUCCESSFUL_RESULT = new ValidationResult(true, null);

    private FailedValidationListener mListener;
    private AnswerProvider mAnswerProvider;

    public DefaultValidator(FailedValidationListener failedValidationListener, AnswerProvider answerProvider) {
        mListener = failedValidationListener;
        mAnswerProvider = answerProvider;
    }

    @Override
    public ValidationResult validate(List<Validation> validations, String answer) {
        if (validations == null || validations.isEmpty()) {
            return SUCCESSFUL_RESULT;
        }
        for (Validation validation : validations) {
            if (!isConditionMet(validation, answer)) {
                return new ValidationResult(false, validation.onFailMessage);
            }
        }
        return SUCCESSFUL_RESULT;
    }

    @Override
    public ValidationResult validate(List<Validation> validations, Map<String, String> answers) {
        if (validations == null || validations.isEmpty()) {
            return SUCCESSFUL_RESULT;
        }
        for (Validation validation : validations) {
            String answer = answers.get(validation.forLabel);
            if (answer == null) {
                continue;
            }
            if (!isConditionMet(validation, answer)) {
                return new ValidationResult(false, validation.onFailMessage);
            }
        }
        return SUCCESSFUL_RESULT;
    }

    private boolean isConditionMet(Validation validation, String answer) {
        Double value = validation.value;
        if (validation.answerToQuestionId != null) {
            if (mAnswerProvider == null) {
                throw new IllegalStateException("Validation requires a non-null AnswerProvider");
            }
            value = Double.parseDouble(mAnswerProvider.answerFor(validation.answerToQuestionId));
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
