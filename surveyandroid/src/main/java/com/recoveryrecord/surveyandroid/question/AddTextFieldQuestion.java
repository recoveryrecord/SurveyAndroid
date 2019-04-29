package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTextFieldQuestion extends Question {

    @JsonProperty("input_type")
    public String inputType;
}
