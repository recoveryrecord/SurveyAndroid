package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class DynamicLabelTextFieldQuestion extends Question {

    @JsonProperty("label_options")
    public ArrayList<ArrayList<String>> labelOptions;

    @JsonProperty("input_type")
    public String inputType;

    public ArrayList<Validation> validations;
}
