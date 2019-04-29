package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class MultiTextFieldQuestion extends Question {

    @JsonProperty("max_chars")
    public String maxChars;

    public ArrayList<Field> fields;

    public static class Field {
        public String label;

        @JsonProperty("input_type")
        public String inputType;
    }
}
