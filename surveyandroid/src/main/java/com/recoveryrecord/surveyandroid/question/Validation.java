package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Validation {

    public String operation;

    public Double value;

    @JsonProperty("answer_to_question_id")
    public String answerToQuestionId;

    @JsonProperty("on_fail_message")
    public String onFailMessage;

    @JsonProperty("for_label")
    public String forLabel;
}
