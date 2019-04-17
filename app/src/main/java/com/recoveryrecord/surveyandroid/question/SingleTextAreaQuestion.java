package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SingleTextAreaQuestion extends Question {

    @JsonProperty("max_chars")
    public String maxChars;

    public ArrayList<Validation> validations;
}
