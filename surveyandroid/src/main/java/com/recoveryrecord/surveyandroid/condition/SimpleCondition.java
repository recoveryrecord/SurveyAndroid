package com.recoveryrecord.surveyandroid.condition;

import android.support.annotation.NonNull;

public class SimpleCondition extends Condition {
    public String id;
    public String subid;
    public String value;

    @NonNull
    @Override
    public String toString() {
        return "SimpleCondition: id = " + id + ", subid = " + subid + ", operation = " + operation + ", value = " + value;
    }
}
