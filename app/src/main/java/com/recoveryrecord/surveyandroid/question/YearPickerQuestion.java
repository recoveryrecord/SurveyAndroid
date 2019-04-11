package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YearPickerQuestion extends Question {

    @JsonProperty("min_year")
    public String minYear;

    @JsonProperty("max_year")
    public String maxYear;

    @JsonProperty("num_years")
    public String numYears;

    @JsonProperty("initial_year")
    public String initialYear;

    @JsonProperty("sort_order")
    public String sortOrder;
}
