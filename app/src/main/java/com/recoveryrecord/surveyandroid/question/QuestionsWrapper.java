package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionsWrapper {
    public ArrayList<Question> questions;
}
