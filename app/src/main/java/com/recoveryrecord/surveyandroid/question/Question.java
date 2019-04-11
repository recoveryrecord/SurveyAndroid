package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

    public String id;
    public String header;
    public String question;

    @JsonProperty("question_type")
    public String questionType;

    @JsonProperty("sub_questions")
    public ArrayList<Question> subQuestions;
}
