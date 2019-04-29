package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionsWrapper {
    public ArrayList<Question> questions;
    public SubmitData submit;

    public static class SubmitData {
        @JsonProperty("button_title")
        public String buttonTitle;
        public String url;
    }
}
