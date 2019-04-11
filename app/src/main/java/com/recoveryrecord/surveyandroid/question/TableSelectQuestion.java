package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TableSelectQuestion extends Question {

    public ArrayList<Option> options;

    @JsonProperty("table_questions")
    public ArrayList<TableQuestion> tableQuestions;

    public static class TableQuestion {
        public String id;
        public String title;
    }
}
