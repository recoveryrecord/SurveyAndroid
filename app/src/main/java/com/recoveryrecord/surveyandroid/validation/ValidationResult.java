package com.recoveryrecord.surveyandroid.validation;

public class ValidationResult {
    public boolean isValid;
    public String failedMessage;

    public ValidationResult(boolean isValid, String failedMessage) {
        this.isValid = isValid;
        this.failedMessage = failedMessage;
    }
}
