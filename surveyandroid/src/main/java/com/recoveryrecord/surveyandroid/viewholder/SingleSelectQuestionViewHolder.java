package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.Answer;
import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.Option;
import com.recoveryrecord.surveyandroid.question.OtherOption;
import com.recoveryrecord.surveyandroid.question.SingleSelectQuestion;
import com.recoveryrecord.surveyandroid.util.KeyboardUtil;
import com.recoveryrecord.surveyandroid.util.SimpleTextWatcher;
import com.recoveryrecord.surveyandroid.util.TopAlignedRadioButton;

public class SingleSelectQuestionViewHolder extends QuestionViewHolder<SingleSelectQuestion> {

    private static final String CHECKED_BUTTON_TITLE_KEY = "checked_button_title";
    private static final String EDIT_TEXT_KEY = "edit_text";
    private static final String HAS_BEEN_ANSWERED_KEY = "has_been_answered_key";

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
            final RadioButton radioButton = new TopAlignedRadioButton(getContext());
            radioButton.setText(option.title);
            if (option instanceof OtherOption) {
                radioButton.setTag(R.id.is_other, Boolean.TRUE);
                radioButton.setTag(R.id.input_type_number, ((OtherOption) option).type.equals("number"));
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
                    editOther.setInputType(isButtonTypeNumber(selectedButton) ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);
                    otherSection.setVisibility(View.VISIBLE);
                    editOther.requestFocus();
                    KeyboardUtil.showKeyboard(getContext(), editOther);
                } else {
                    otherSection.setVisibility(View.GONE);
                    questionState.setAnswer(new Answer(selectedButton.getText().toString()));
                    setHasBeenAnswered(questionState);
                }


            }
        });
        editOther.setText(questionState.getString(EDIT_TEXT_KEY));
        editTextWatcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                questionState.put(EDIT_TEXT_KEY, s.toString());
                if (hasBeenAnswered(questionState)) {
                    questionState.setAnswer(new Answer(s.toString()));
                }
            }
        };
        editOther.addTextChangedListener(editTextWatcher);
        editOther.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    onNext(questionState);
                    return true;
                }
                return false;
            }
        });
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

    private boolean isButtonTypeNumber(Button button) {
        return button.getTag(R.id.input_type_number) != null && button.getTag(R.id.input_type_number) == Boolean.TRUE;
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
        editOther.setOnEditorActionListener(null);
        nextButton.setOnClickListener(null);
    }

    private void onNext(QuestionState questionState) {
        RadioButton selectedButton = answerSelector.findViewById(answerSelector.getCheckedRadioButtonId());
        if (isButtonOther(selectedButton)) {
            questionState.setAnswer(new Answer(editOther.getText().toString()));
            editOther.clearFocus();
            KeyboardUtil.hideKeyboard(getContext(), editOther);
        }
        setHasBeenAnswered(questionState);
    }

    // Returns true if the answer for this has been set before
    private boolean hasBeenAnswered(QuestionState questionState) {
        return questionState.getBool(HAS_BEEN_ANSWERED_KEY, false);
    }

    private void setHasBeenAnswered(QuestionState questionState) {
        questionState.put(HAS_BEEN_ANSWERED_KEY, true);
    }
}
