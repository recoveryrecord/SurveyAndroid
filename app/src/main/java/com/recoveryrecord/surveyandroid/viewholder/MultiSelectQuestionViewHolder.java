package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.recoveryrecord.surveyandroid.Answer;
import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.MultiSelectQuestion;
import com.recoveryrecord.surveyandroid.question.Option;
import com.recoveryrecord.surveyandroid.question.OtherOption;
import com.recoveryrecord.surveyandroid.util.SimpleTextWatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiSelectQuestionViewHolder extends QuestionViewHolder<MultiSelectQuestion> {
    private static final String CHECKED_TITLES_KEY = "checked_titles";
    private static final String ANSWER_ON_CHECK_CHANGE_KEY = "answer_on_check_change_key";
    private static final String EDIT_TEXT_KEY = "%s_edit_text_key";

    private ViewGroup answerCheckboxContainer;
    private Button nextButton;
    private Map<String, View> otherMap = new HashMap<>();

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
            TextWatcher editTextWatcher = new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    questionState.put(getEditTextKey(option.title), s.toString());
                    if (questionState.getBool(ANSWER_ON_CHECK_CHANGE_KEY, false)) {
                        onNext(questionState);
                    }
                }
            };
            answerCheckboxContainer.addView(checkBox);
            if (option instanceof OtherOption) {
                View otherSection = layoutInflater.inflate(R.layout.view_other_multi_select, answerCheckboxContainer, false);
                EditText editText = otherSection.findViewById(R.id.edit_other);
                editText.addTextChangedListener(editTextWatcher);
                otherSection.setVisibility(checkedTitles.contains(option.title) ? View.VISIBLE : View.GONE);
                editText.setText(questionState.getString(getEditTextKey(option.title)));
                answerCheckboxContainer.addView(otherSection);
                otherMap.put(checkBox.getText().toString(), otherSection);
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    View otherSection = otherMap.get(buttonView.getText().toString());
                    if (otherSection != null) {
                        otherSection.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                    }
                    if (isChecked) {
                        questionState.addStringToList(CHECKED_TITLES_KEY, buttonView.getText().toString());
                    } else {
                        questionState.removeStringFromList(CHECKED_TITLES_KEY, buttonView.getText().toString());
                    }
                    if (questionState.getBool(ANSWER_ON_CHECK_CHANGE_KEY, false)) {
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

    private ArrayList<String> getSelectedCheckboxTitles() {
        ArrayList<String> checkedTitles = new ArrayList<>();
        for (int i = 0; i < answerCheckboxContainer.getChildCount(); i++) {
            View child = answerCheckboxContainer.getChildAt(i);
            if (child instanceof CheckBox && ((CheckBox) child).isChecked()) {
                View otherSection = otherMap.get(((CheckBox) child).getText().toString());
                if (otherSection != null) {
                    EditText editText = otherSection.findViewById(R.id.edit_other);
                    checkedTitles.add(editText.getText().toString());
                } else {
                    checkedTitles.add(((CheckBox) child).getText().toString());
                }
            }
        }
        return checkedTitles;
    }

    @Override
    protected void resetState() {
        super.resetState();
        answerCheckboxContainer.removeAllViews();
        otherMap.clear();
        nextButton.setOnClickListener(null);
    }

    private void onNext(QuestionState questionState) {
        questionState.setAnswer(new Answer(getSelectedCheckboxTitles()));
        questionState.put(ANSWER_ON_CHECK_CHANGE_KEY, true);
    }
}
