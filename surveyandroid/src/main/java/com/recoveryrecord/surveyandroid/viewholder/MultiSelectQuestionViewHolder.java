package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.Answer;
import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.MultiSelectQuestion;
import com.recoveryrecord.surveyandroid.question.Option;
import com.recoveryrecord.surveyandroid.question.OtherOption;
import com.recoveryrecord.surveyandroid.util.KeyboardUtil;
import com.recoveryrecord.surveyandroid.util.SimpleTextWatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiSelectQuestionViewHolder extends QuestionViewHolder<MultiSelectQuestion> {
    private static final String CHECKED_TITLES_KEY = "checked_titles";
    private static final String HAS_BEEN_ANSWERED_KEY = "has_been_answered_key";
    private static final String EDIT_TEXT_KEY = "%s_edit_text_key";

    private ViewGroup answerCheckboxContainer;
    private Button nextButton;
    private Map<String, OtherView> otherMap = new HashMap<>();
    TextWatcher mEditTextWatcher;

    public MultiSelectQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);

        answerCheckboxContainer = itemView.findViewById(R.id.answer_checkbox_container);
        nextButton = itemView.findViewById(R.id.next_button);
    }

    public void bind(MultiSelectQuestion question, final QuestionState questionState) {
        super.bind(question);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        ArrayList<String> checkedTitles = questionState.getList(CHECKED_TITLES_KEY, new ArrayList<String>());
        for (final Option option : question.options) {
            final CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(option.title);
            checkBox.setChecked(checkedTitles.contains(option.title));
            mEditTextWatcher = new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    questionState.put(getEditTextKey(option.title), s.toString());
                    if (hasBeenAnswered(questionState)) {
                        onNext(questionState);
                    }
                }
            };
            answerCheckboxContainer.addView(checkBox);
            if (option instanceof OtherOption) {
                OtherView otherView = new OtherView(layoutInflater.inflate(R.layout.view_other_multi_select, answerCheckboxContainer, false));
                otherView.editText.setInputType(((OtherOption) option).type.equals("number") ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);
                otherView.setVisibility(checkedTitles.contains(option.title) ? View.VISIBLE : View.GONE);
                otherView.editText.setText(questionState.getString(getEditTextKey(option.title)));
                otherView.editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                otherView.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            onNext(questionState);
                            return true;
                        }
                        return false;
                    }
                });
                otherView.editText.addTextChangedListener(mEditTextWatcher);
                answerCheckboxContainer.addView(otherView.otherSection);
                otherMap.put(checkBox.getText().toString(), otherView);
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    OtherView otherView = otherMap.get(buttonView.getText().toString());
                    if (otherView != null) {
                        otherView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                        if (isChecked) {
                            otherView.editText.requestFocus();
                            KeyboardUtil.showKeyboard(getContext(), otherView.editText);
                        }
                    }
                    if (isChecked) {
                        questionState.addStringToList(CHECKED_TITLES_KEY, buttonView.getText().toString());
                    } else {
                        questionState.removeStringFromList(CHECKED_TITLES_KEY, buttonView.getText().toString());
                    }
                    if (hasBeenAnswered(questionState)) {
                        onNext(questionState);
                    }
                }
            });
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext(questionState);
            }
        });
    }

    private String getEditTextKey(String title) {
        return String.format(EDIT_TEXT_KEY, title);
    }

    private ArrayList<String> getCheckboxAnswers() {
        ArrayList<String> checkedTitles = new ArrayList<>();
        for (int i = 0; i < answerCheckboxContainer.getChildCount(); i++) {
            View child = answerCheckboxContainer.getChildAt(i);
            if (!(child instanceof CheckBox) || !((CheckBox) child).isChecked()) {
                continue;
            }
            String checkboxTitle = ((CheckBox) child).getText().toString();
            OtherView otherView = otherMap.get(checkboxTitle);
            if (otherView != null) {
                checkedTitles.add(otherView.editText.getText().toString());
            } else {
                checkedTitles.add(checkboxTitle);
            }
        }
        return checkedTitles;
    }

    @Override
    protected void resetState() {
        super.resetState();
        answerCheckboxContainer.removeAllViews();
        for (OtherView otherView : otherMap.values()) {
            if (mEditTextWatcher != null) {
                otherView.editText.removeTextChangedListener(mEditTextWatcher);
            }
            otherView.editText.setOnEditorActionListener(null);
        }
        otherMap.clear();
        nextButton.setOnClickListener(null);
    }

    private void onNext(QuestionState questionState) {
        questionState.setAnswer(new Answer(getCheckboxAnswers()));
        setHasBeenAnswered(questionState);
        KeyboardUtil.hideKeyboard(getContext(), answerCheckboxContainer);
    }

    // Returns true if the answer for this has been set before
    private boolean hasBeenAnswered(QuestionState questionState) {
        return questionState.getBool(HAS_BEEN_ANSWERED_KEY, false);
    }

    private void setHasBeenAnswered(QuestionState questionState) {
        questionState.put(HAS_BEEN_ANSWERED_KEY, true);
    }

    private class OtherView {
        View otherSection;
        EditText editText;

        OtherView(View otherSection) {
            this.otherSection = otherSection;
            this.editText = otherSection.findViewById(R.id.edit_other);
        }

        void setVisibility(int visibility) {
            otherSection.setVisibility(visibility);
        }
    }
}
