package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SingleTextFieldQuestion extends Question {

    public String label;

    @JsonProperty("input_type")
    public String input_type;

    @JsonProperty("max_chars")
    public String maxChars;

    public ArrayList<Validation> validations;
}
