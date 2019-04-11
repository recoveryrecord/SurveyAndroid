package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SegmentSelectQuestionType extends Question {

    public ArrayList<String> values;

    @JsonProperty("low_tag")
    public String lowTag;

    @JsonProperty("high_tag")
    public String highTag;
}
