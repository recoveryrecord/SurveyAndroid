package com.recoveryrecord.surveyandroid.example;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.recoveryrecord.surveyandroid.Answer;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.SurveyActivity;
import com.recoveryrecord.surveyandroid.condition.CustomConditionHandler;

import java.util.Calendar;
import java.util.Map;

public class ExampleSurveyActivity extends SurveyActivity implements CustomConditionHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getSurveyTitle() {
        return getString(R.string.example_survey);
    }

    @Override
    protected String getJsonFilename() {
        return "ExampleQuestions.json";
    }

    @Override
    protected CustomConditionHandler getCustomConditionHandler() {
        return this;
    }

    @Override
    public boolean isConditionMet(Map<String, Answer> answers, Map<String, String> extra) {
        String id = extra.get("id");
        if (id != null && id.equals("check_age")) {
            if (answers.get("birthyear") == null || answers.get("age") == null || extra.get("wiggle_room") == null) {
                return false;
            }
            String birthYearStr = answers.get("birthyear").getValue();
            Integer birthYear = Integer.valueOf(birthYearStr);
            String ageStr = answers.get("age").getValue();
            Integer age = Integer.valueOf(ageStr);
            Integer wiggleRoom = Integer.valueOf(extra.get("wiggle_room"));
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            return Math.abs(birthYear + age - currentYear) > wiggleRoom;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (!getOnSurveyStateChangedListener().scrollBackOneQuestion()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.close_survey)
                    .setMessage(R.string.are_you_sure_you_want_to_close)
                    .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ExampleSurveyActivity.super.onBackPressed();
                }
            }).show();
        }
    }
}
