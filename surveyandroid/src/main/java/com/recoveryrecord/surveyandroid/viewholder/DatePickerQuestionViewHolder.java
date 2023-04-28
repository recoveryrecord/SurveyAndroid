package com.recoveryrecord.surveyandroid.viewholder;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.recoveryrecord.surveyandroid.Answer;
import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.DatePickerQuestion;

import java.util.Calendar;

public class DatePickerQuestionViewHolder extends QuestionViewHolder<DatePickerQuestion> {

    private static final String HAS_BEEN_ANSWERED_KEY = "has_been_answered_key";

    private TextInputEditText answerEdit;

    private Button nextButton;



    public DatePickerQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        nextButton = itemView.findViewById(R.id.next_button);
        answerEdit = itemView.findViewById(R.id.answer_edit);
    }

    public void bind(DatePickerQuestion question, QuestionState questionState) {
        super.bind(question);

        answerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(questionState);
            }
        });

        answerEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDateDialog(questionState);
            }
        });

        answerEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    onNext(questionState, answerEdit.getText().toString());
                    return true;
                }
                return false;
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext(questionState, answerEdit.getText().toString());
            }
        });
    }

    @Override
    protected void resetState() {
        super.resetState();
        nextButton.setOnClickListener(null);
        answerEdit.setOnEditorActionListener(null);
        answerEdit.setOnFocusChangeListener(null);
        answerEdit.setOnClickListener(null);
        answerEdit.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void onNext(QuestionState questionState, String date) {
        if (date != null && !date.isEmpty()){
            questionState.setAnswer(new Answer(date));
            setHasBeenAnswered(questionState);
        } else {
            Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
        }
    }

    // Returns true if the answer for this has been set before
    private boolean hasBeenAnswered(QuestionState questionState) {
        return questionState.getBool(HAS_BEEN_ANSWERED_KEY, false);
    }

    private void setHasBeenAnswered(QuestionState questionState) {
        questionState.put(HAS_BEEN_ANSWERED_KEY, true);
    }

    private void showDateDialog(QuestionState questionState) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // on below line we are creating a variable for date picker dialog.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context.
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        answerEdit.setText(date);
                        if(hasBeenAnswered(questionState)) {
                            onNext(questionState, date);
                        }
                    }
                },
                // on below line we are passing year,
                // month and day for selected date in our date picker.
                currentYear, currentMonth, currentDay);
        // at last we are calling show to
        // display our date picker dialog.
        datePickerDialog.show();
    }
}
