package com.recoveryrecord.surveyandroid;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recoveryrecord.surveyandroid.question.AddTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.DatePickerQuestion;
import com.recoveryrecord.surveyandroid.question.DynamicLabelTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.MultiSelectQuestion;
import com.recoveryrecord.surveyandroid.question.MultiTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.Question;
import com.recoveryrecord.surveyandroid.question.SegmentSelectQuestion;
import com.recoveryrecord.surveyandroid.question.SingleSelectQuestion;
import com.recoveryrecord.surveyandroid.question.SingleTextAreaQuestion;
import com.recoveryrecord.surveyandroid.question.SingleTextFieldQuestion;
import com.recoveryrecord.surveyandroid.question.TableSelectQuestion;
import com.recoveryrecord.surveyandroid.question.YearPickerQuestion;
import com.recoveryrecord.surveyandroid.viewholder.AddTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.DatePickerQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.DynamicLabelTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.MultiSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.MultiTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SegmentSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SingleSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SingleTextAreaQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SingleTextFieldQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.SubmitViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.TableSelectQuestionViewHolder;
import com.recoveryrecord.surveyandroid.viewholder.YearPickerQuestionViewHolder;

public class SurveyQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private SurveyState mState;

    public enum QuestionType {
        SINGLE_SELECT("single_select"),
        MULTI_SELECT("multi_select"),
        YEAR_PICKER("year_picker"),
        DATE_PICKER("date_picker"),
        SINGLE_TEXT_FIELD("single_text_field"),
        SINGLE_TEXT_AREA("single_text_area"),
        MULTI_TEXT_FIELD("multi_text_field"),
        DYNAMIC_LABEL_TEXT_FIELD("dynamic_label_text_field"),
        ADD_TEXT_FIELD("add_text_field"),
        SEGMENT_SELECT("segment_select"),
        TABLE_SELECT("table_select"),
        SUBMIT("submit");

        private String mValue;

        QuestionType(String value) {
            mValue = value;
        }

        @Override
        @NonNull
        public String toString() {
            return mValue;
        }

        public static QuestionType fromString(String value) {
            for (QuestionType questionType : QuestionType.values()) {
                if (questionType.mValue.equals(value)) {
                    return questionType;
                }
            }
            return null;
        }
    }

    SurveyQuestionAdapter(Context context, SurveyState state) {
        mContext = context;
        mState = state;
    }

    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        QuestionType questionType = QuestionType.values()[viewType];
        View view = LayoutInflater.from(getContext()).inflate(getLayout(questionType), viewGroup, false);
        switch (questionType) {
            case SINGLE_SELECT:
                return new SingleSelectQuestionViewHolder(getContext(), view);
            case MULTI_SELECT:
                return new MultiSelectQuestionViewHolder(getContext(), view);
            case YEAR_PICKER:
                return new YearPickerQuestionViewHolder(getContext(), view);
            case DATE_PICKER:
                return new DatePickerQuestionViewHolder(getContext(), view);
            case SINGLE_TEXT_FIELD:
                return new SingleTextFieldQuestionViewHolder(getContext(), view, mState.getValidator(), mState);
            case SINGLE_TEXT_AREA:
                return new SingleTextAreaQuestionViewHolder(getContext(), view, mState.getValidator(), mState);
            case MULTI_TEXT_FIELD:
                return new MultiTextFieldQuestionViewHolder(getContext(), view);
            case DYNAMIC_LABEL_TEXT_FIELD:
                return new DynamicLabelTextFieldQuestionViewHolder(getContext(), view);
            case ADD_TEXT_FIELD:
                return new AddTextFieldQuestionViewHolder(getContext(), view);
            case SEGMENT_SELECT:
                return new SegmentSelectQuestionViewHolder(getContext(), view);
            case TABLE_SELECT:
                return new TableSelectQuestionViewHolder(getContext(), view);
            case SUBMIT:
                return new SubmitViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Question question = null;
        QuestionType questionType;
        QuestionState questionState = null;
        if (mState.isSubmitPosition(position)) {
            questionType = QuestionType.SUBMIT;
        } else {
            question = getItem(position);
            questionType = getQuestionType(position);
            questionState = mState.getStateFor(question.id);
        }
        switch (questionType) {
            case SINGLE_SELECT:
                if (viewHolder instanceof SingleSelectQuestionViewHolder && question instanceof SingleSelectQuestion) {
                    ((SingleSelectQuestionViewHolder) viewHolder).bind((SingleSelectQuestion) question, questionState);
                }
                break;
            case MULTI_SELECT:
                if (viewHolder instanceof MultiSelectQuestionViewHolder && question instanceof MultiSelectQuestion) {
                    ((MultiSelectQuestionViewHolder) viewHolder).bind((MultiSelectQuestion) question, questionState);
                }
                break;
            case YEAR_PICKER:
                if (viewHolder instanceof YearPickerQuestionViewHolder && question instanceof YearPickerQuestion) {
                    ((YearPickerQuestionViewHolder) viewHolder).bind((YearPickerQuestion) question, questionState);
                }
                break;
            case DATE_PICKER:
                if (viewHolder instanceof DatePickerQuestionViewHolder && question instanceof DatePickerQuestion) {
                    ((DatePickerQuestionViewHolder) viewHolder).bind((DatePickerQuestion) question, questionState);
                }
                break;
            case SINGLE_TEXT_FIELD:
                if (viewHolder instanceof SingleTextFieldQuestionViewHolder && question instanceof SingleTextFieldQuestion) {
                    ((SingleTextFieldQuestionViewHolder) viewHolder).bind((SingleTextFieldQuestion) question, questionState);
                }
                break;
            case SINGLE_TEXT_AREA:
                if (viewHolder instanceof SingleTextAreaQuestionViewHolder && question instanceof SingleTextAreaQuestion) {
                    ((SingleTextAreaQuestionViewHolder) viewHolder).bind((SingleTextAreaQuestion) question, questionState);
                }
            case MULTI_TEXT_FIELD:
                if (viewHolder instanceof MultiTextFieldQuestionViewHolder && question instanceof MultiTextFieldQuestion) {
                    ((MultiTextFieldQuestionViewHolder) viewHolder).bind((MultiTextFieldQuestion) question, questionState);
                }
                break;
            case DYNAMIC_LABEL_TEXT_FIELD:
                if (viewHolder instanceof DynamicLabelTextFieldQuestionViewHolder && question instanceof DynamicLabelTextFieldQuestion) {
                    ((DynamicLabelTextFieldQuestionViewHolder) viewHolder).bind((DynamicLabelTextFieldQuestion) question, questionState);
                }                break;
            case ADD_TEXT_FIELD:
                if (viewHolder instanceof AddTextFieldQuestionViewHolder && question instanceof AddTextFieldQuestion) {
                    ((AddTextFieldQuestionViewHolder) viewHolder).bind((AddTextFieldQuestion) question, questionState);
                }
                break;
            case SEGMENT_SELECT:
                if (viewHolder instanceof SegmentSelectQuestionViewHolder && question instanceof SegmentSelectQuestion) {
                    ((SegmentSelectQuestionViewHolder) viewHolder).bind((SegmentSelectQuestion) question, questionState);
                }
                break;
            case TABLE_SELECT:
                if (viewHolder instanceof TableSelectQuestionViewHolder && question instanceof TableSelectQuestion) {
                    ((TableSelectQuestionViewHolder) viewHolder).bind((TableSelectQuestion) question, questionState);
                }
                break;
            case SUBMIT:
                if (viewHolder instanceof SubmitViewHolder) {
                    ((SubmitViewHolder) viewHolder).bind(mState.getSubmitData(), mState, mState.getSubmitSurveyHandler());
                }
        }
    }

    private @LayoutRes int getLayout(QuestionType questionType) {
        switch (questionType) {
            case SINGLE_SELECT:
                return R.layout.view_single_select_question;
            case MULTI_SELECT:
                return R.layout.view_multi_select_question;
            case YEAR_PICKER:
                return R.layout.view_year_picker_question;
            case DATE_PICKER:
                return R.layout.view_date_picker_question;
            case SINGLE_TEXT_FIELD:
                return R.layout.view_single_text_field_question;
            case SINGLE_TEXT_AREA:
                return R.layout.view_single_text_area_question;
            case MULTI_TEXT_FIELD:
                return R.layout.view_multi_text_field_question;
            case DYNAMIC_LABEL_TEXT_FIELD:
                return R.layout.view_dynamic_label_text_field_question;
            case ADD_TEXT_FIELD:
                return R.layout.view_add_text_field_question;
            case SEGMENT_SELECT:
                return R.layout.view_segment_select_question;
            case TABLE_SELECT:
                return R.layout.view_table_select_question;
            case SUBMIT:
                return R.layout.view_submit_button;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getQuestionType(position).ordinal();
    }

    private Question getItem(int position) {
        return mState.getQuestionFor(position);
    }

    private QuestionType getQuestionType(int position) {
        if (mState.isSubmitPosition(position)) {
            return QuestionType.SUBMIT;
        }
        Question question = getItem(position);
        return QuestionType.fromString(question.questionType);
    }

    @Override
    public int getItemCount() {
        return mState.getVisibleQuestionCount();
    }
}
