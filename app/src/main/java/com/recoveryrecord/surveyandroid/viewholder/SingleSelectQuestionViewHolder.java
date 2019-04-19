package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.Option;
import com.recoveryrecord.surveyandroid.question.OtherOption;
import com.recoveryrecord.surveyandroid.question.SingleSelectQuestion;
import com.recoveryrecord.surveyandroid.util.SimpleTextWatcher;

public class SingleSelectQuestionViewHolder extends QuestionViewHolder<SingleSelectQuestion> {

    private static final String CHECKED_BUTTON_TITLE_KEY = "checked_button_title";
    private static final String EDIT_TEXT_KEY = "edit_text";
    private static final String ANSWER_ON_EDIT_UPDATE_KEY = "answer_on_edit_update_key";

    private RadioGroup answerSelector;
    private ViewGroup otherSection;
    private EditText editOther;
    private TextWatcher editTextWatcher;
    private Button nextButton;

    public SingleSelectQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        answerSelector = itemView.findViewById(R.id.answer_selector);
        otherSection = itemView.findViewById(R.id.other_section);
        editOther = itemView.findViewById(R.id.edit_other);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    public void bind(final SingleSelectQuestion question, final QuestionState questionState) {
        super.bind(question);
        int checkedId = -1;
        for (Option option : question.options) {
            final RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(option.title);
            if (option instanceof OtherOption) {
                radioButton.setTag(R.id.is_other, Boolean.TRUE);
                if (shouldCheckButton(questionState, option.title)) {
                    otherSection.setVisibility(View.VISIBLE);
                }
            }
            answerSelector.addView(radioButton);
            if (shouldCheckButton(questionState, option.title)) {
                checkedId = radioButton.getId();
            }
        }
        answerSelector.check(checkedId);
        answerSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedButton = answerSelector.findViewById(checkedId);
                questionState.put(CHECKED_BUTTON_TITLE_KEY, selectedButton.getText().toString());
                if (isButtonOther(selectedButton)) {
                    otherSection.setVisibility(View.VISIBLE);
                } else {
                    otherSection.setVisibility(View.GONE);
                    questionState.setAnswer(selectedButton.getText().toString());
                    questionState.put(ANSWER_ON_EDIT_UPDATE_KEY, true);
                }


            }
        });
        editOther.setText(questionState.getString(EDIT_TEXT_KEY));
        editTextWatcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                questionState.put(EDIT_TEXT_KEY, s.toString());
                if (questionState.getBool(ANSWER_ON_EDIT_UPDATE_KEY, false)) {
                    questionState.setAnswer(s.toString());
                }
            }
        };
        editOther.addTextChangedListener(editTextWatcher);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext(questionState);
            }
        });
    }

    private boolean shouldCheckButton(QuestionState questionState, String title) {
        return title.equals(questionState.getString(CHECKED_BUTTON_TITLE_KEY));
    }

    private boolean isButtonOther(Button button) {
        return button.getTag(R.id.is_other) != null && button.getTag(R.id.is_other) == Boolean.TRUE;
    }

    @Override
    protected void resetState() {
        super.resetState();
        answerSelector.removeAllViews();
        answerSelector.setOnCheckedChangeListener(null);
        if (editTextWatcher != null) {
            editOther.removeTextChangedListener(editTextWatcher);
        }
        otherSection.setVisibility(View.GONE);
        editOther.setText(null);
        nextButton.setOnClickListener(null);
    }

    private void onNext(QuestionState questionState) {
        RadioButton selectedButton = answerSelector.findViewById(answerSelector.getCheckedRadioButtonId());
        if (isButtonOther(selectedButton)) {
            questionState.setAnswer(editOther.getText().toString());
        }
        questionState.put(ANSWER_ON_EDIT_UPDATE_KEY, true);
    }
}
