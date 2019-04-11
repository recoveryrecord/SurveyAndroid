package com.recoveryrecord.surveyandroid.question;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatePickerQuestion extends Question {

    public String date;

    @JsonProperty("min_date")
    public String minDate;

    @JsonProperty("max_date")
    public String maxDate;

    @JsonProperty("date_diff")
    public DateDiff dateDiff;

    public static class DateDiff {
        public Integer year;
        public Integer month;
        public Integer day;
    }
}
