package com.recoveryrecord.surveyandroid.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.recoveryrecord.surveyandroid.Answer;
import com.recoveryrecord.surveyandroid.QuestionState;
import com.recoveryrecord.surveyandroid.R;
import com.recoveryrecord.surveyandroid.question.AddTextFieldQuestion;
import com.recoveryrecord.surveyandroid.util.KeyboardUtil;

import java.util.ArrayList;

public class AddTextFieldQuestionViewHolder extends QuestionViewHolder<AddTextFieldQuestion> {
    private static final String HAS_BEEN_ANSWERED_KEY = "has_been_answered_key";

    private ViewGroup answerContainer;
    private ImageButton addButton;
    private Button nextButton;
    private ArrayList<AddTextFieldViewWrapper> mAddTextFieldViewList = new ArrayList<>();

    public AddTextFieldQuestionViewHolder(Context context, @NonNull View itemView) {
        super(context, itemView);
        answerContainer = itemView.findViewById(R.id.answer_container);
        addButton = itemView.findViewById(R.id.add_button);
        nextButton = itemView.findViewById(R.id.next_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNTextInputs(1);
            }
        });
    }

    public void bind(AddTextFieldQuestion question, final QuestionState questionState) {
        super.bind(question);
        if (questionState.isAnswered() && questionState.getAnswer().isList()) {
            ArrayList<String> answers = questionState.getAnswer().getValueList();
            addNTextInputs(answers.size(), answers);
        } else {
            addNTextInputs(1);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext(questionState);
            }
        });
    }

    private void addNTextInputs(int number) {
        addNTextInputs(number, new ArrayList<String>(), true);
    }

    private void addNTextInputs(int number, ArrayList<String> values) {
        addNTextInputs(number, values, false);
    }

    private void addNTextInputs(int number, ArrayList<String> values, boolean focusOnLast) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < number; i++) {
            AddTextFieldViewWrapper view = new AddTextFieldViewWrapper(inflater.inflate(R.layout.text_input_answer, answerContainer, false));
            answerContainer.addView(view.getRoot());
            mAddTextFieldViewList.add(view);
            if (values.size() > i) {
                view.setText(values.get(i));
            }
            view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        addNTextInputs(1);
                        return true;
                    }
                    return false;
                }
            });
            if (focusOnLast && i == number - 1) {
                view.requestFocus();
            }
        }
    }

    // Returns true if the answer for this has been set before
    private boolean hasBeenAnswered(QuestionState questionState) {
        return questionState.getBool(HAS_BEEN_ANSWERED_KEY, false);
    }

    private void setHasBeenAnswered(QuestionState questionState) {
        questionState.put(HAS_BEEN_ANSWERED_KEY, true);
    }

    private ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        for (AddTextFieldViewWrapper viewWrapper : mAddTextFieldViewList) {
            if (!viewWrapper.isEmpty()) {
                answers.add(viewWrapper.getText());
            }
        }
        return answers;
    }

    private void onNext(QuestionState questionState) {
        questionState.setAnswer(new Answer(getAnswers()));
        setHasBeenAnswered(questionState);
        KeyboardUtil.hideKeyboard(getContext(), answerContainer);
    }

    @Override
    protected void resetState() {
        super.resetState();
        for (AddTextFieldViewWrapper viewWrapper : mAddTextFieldViewList) {
            viewWrapper.removeOnEditorActionListener();
        }
        mAddTextFieldViewList.clear();
        answerContainer.removeAllViews();
        nextButton.setOnClickListener(null);
    }

    private class AddTextFieldViewWrapper {
        View inputLayout;
        EditText editText;

        AddTextFieldViewWrapper(View inputLayout) {
            this.inputLayout = inputLayout;
            this.editText = inputLayout.findViewById(R.id.answer_edit);
        }

        View getRoot() {
            return this.inputLayout;
        }

        void setText(String text) {
            this.editText.setText(text);
        }

        boolean isEmpty() {
            return TextUtils.isEmpty(this.editText.getText());
        }

        String getText() {
            return this.editText.getText().toString().trim();
        }

        boolean requestFocus() {
            return this.editText.requestFocus();
        }

        void setOnEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
            this.editText.setOnEditorActionListener(onEditorActionListener);
        }

        void removeOnEditorActionListener() {
            this.editText.setOnEditorActionListener(null);
        }
    }
}
