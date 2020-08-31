# SurveyAndroid
Android Surveys built from declarative definitions (JSON). Many question types, skip logic etc. It is based off of the iOS [SurveyNative project](https://github.com/recoveryrecord/SurveyNative), but currently supports only a subset of that functionality.

## Install
Include the following line in your build.gradle dependencies:

`implementation 'com.recoveryrecord:surveyandroid:1.0'`

## Features

  - Displays a single question at a time, but allow the user to scroll up to read and change previous answers.
  - Takes input from a JSON file.
  - Many supported question types, including single selection, multiple selection, single text field, segment choice, and single text area questions.
  - Support for showing/hiding questions based on previous answers.
  
 ## Features from iOS that are not yet built in Android
  - Support for sub-questions.
  - Several question types: year picker, date picker, multiple text field, dynamic label test field and table select.
  
  ## Example Survey

![Video showing example app](/README/survey_video_720.gif "Survey Video")

## JSON Input

[Example JSON Input file](https://github.com/recoveryrecord/SurveyAndroid/blob/develop/app/src/main/assets/ExampleQuestions.json)

The expected input is an array of `questions` and a `submit` object, detailing how to submit the answers.

### Keys used in Questions

#### Some keys are standard across all question types. Others are only present for some question types.

  - `id` (_String_): Required. Used to key answer. Also used to check show/hide conditions.

  - `header` (_String_): Optional. Displayed as section header.

  - `question` (_String_): Required. Text to display for question.

  - `question_type` (_String_): Required. The chosen type may require additional keys.
    - `single_select` [screenshot](#single_select) | [json](#single_select)
    - `multi_select` [screenshot](#multi_select) | [json](#multi_select)
    - `single_text_field` [screenshot](#single_text_field) | [json](#single_text_field)
    - `single_text_area` [screenshot](#single_text_area) | [json](#single_text_area)
    - `segment_select` [screenshot](#segment_select) | [json](#segment_select)

  - `show_if` (_Conditions_): Optional. If not provided, the question will default to being shown. See the [Structure for Show/Hide question](#structure-for-showhide-question) below for more info.

#### The keys below are specific to certain question types.

  - `options` (_Array of Strings or Other object_): Required for `single_select` and `mult_select` question_types. The Other object is a JSON object with a key for `title`. When selected, the user may enter text into a text field.

  - `label` (_String_): Optional for `single_text_field` question type.

  - `input_type` (_String_): Optional for the `single_text_field` question_type. Can be set to `number` to change the default keyboard to the number keyboard for the text field(s).

  - `max_chars` (_String_): Options for `single_text_field` and `single_text_area` question_types.  Determines the max number of characters the user may enter.

  - `validations` (_Array of Dictionaries_): Optional for `single_text_field` and `single_text_area` question_types. Check value meets the validations when `Next` tapped. If not `validationFailed(message: String)` is called on your `ValidationFailedDelegate`. Validations consist of attributes:
  	-  `operation`
  	-  `value` or `answer_to_question_id`
  	-  `on_fail_message`
  	
  	Supported operations:

    - `equals`
    - `not equals`
    - `greater than`
    - `greater than or equal to`
    - `less than`
    - `less than or equal to`

  - `values` (_Array of String_): Required for `segment_select` question_type. These are the values the user will choose between.

  - `low_tag` (_String_): Optional for `segment_select` question_type. This is a label for the lowest (first) value.

  - `high_tag` (_String_): Optional for `segment_select` question_type. This is a label for the highest (last) value.

### Structure for Show/Hide question

Whether a question is shown or hidden is dependent on the `show_if` key. If the key is missing, the default is to show the question. Both simple conditions and decision trees are supported. The decision trees can contain other decision trees, so you can have fairly complicated logic. There is probably some limit to how far you can nest them.

#### Simple Condition Keys

  - `id` (_String_): Required. This is the id for a question.

  - `subid` (_String_): Optional. Not supported currently.

  - `operation` (_String_): Required. Supported operations:
    - `equals`
    - `not equals`
    - `greater than`
    - `greater than or equal to`
    - `less than`
    - `less than or equal to`
    - `contains`
    - `not contains`

  - `value` (_Any_): Required. This is the value to compare the answer to.

#### Decision Tree Keys

  - `operation` (_String_): Required. Can be `or` or `and`. If you need a combination, you should be able to use nesting to get it.

  - `subconditions` (_Array of Simple Conditions or Decision Trees_): Required.

#### Custom Conditions

If these options are inadequate, you can set a _CustomConditionDelegate_ and use it to make the show/hide decision.

  - `ids` (_Array of Strings_): Required.  Must be non-empty. These are the ids for questions the your delegate needs the answers to in order to perform it's show/hide calculation.  Your delegate will be called as soon as any of the questions are answered, so you may have nil data for one or more answers.

  - `operation` (_String_): Required. Should be set to 'custom'.

  - `extra` (_Dictionary with String keys_): Optional. This will be passed to the _isConditionMet_ method of your _CustomConditionDelegate_.

### Submit

The submit object (a peer to `questions`) requires only two keys, `button_title` and `url`. Both are required strings.

### Question Type Examples

#### single_select

```
{
  "id": "ice_cream",
  "header": "Question 1",
  "question": "What is your favorite ice cream flavor?",
  "question_type": "single_select",
  "options": [
    "Strawberry",
    "Chocolate",
    "Vanilla",
    {
      "title": "Other",
      "type": "freeform"
    }
  ]
}
```

![](/README/ice_cream_0.png "single_select example")

#### multi_select

```
{
  "id": "music_types",
  "header": "Question 6",
  "question": "What types of music do you like?",
  "question_type": "multi_select",
  "options": [
    "Pop",
    "Rock",
    "Rap",
    "Country",
    {
      "title": "Other",
      "type": "freeform"
    }
  ]
}
```

![](/README/music_types_0.png "multi_select example")

#### single_text_field

```
{
  "id": "age",
  "header": "Question 4",
  "question": "What is your current age in years?",
  "question_type": "single_text_field",
  "label": "Years",
  "input_type": "number",
  "max_chars": "2",
  "validations": [
    {
      "operation": "greater than",
      "value": 10,
      "on_fail_message": "Age must be at least 10"
    },
    {
      "operation": "less than",
      "value": 80,
      "on_fail_message": "You must be 80 or younger"
    }
  ]
}
```

![](/README/age_0.png "single_text_field example")

#### single_text_area
```
    {
      "id": "perfect_day",
      "header": "Question 2",
      "question": "How would you describe your perfect day?",
      "question_type": "single_text_area"
    }
```

![](/README/perfect_day_0.png "single_text_area example")

#### segment_select

```
{
  "id": "happiness",
  "header": "Question 8",
  "question": "How happy are you?",
  "question_type": "segment_select",
  "low_tag": "Not happy",
  "high_tag": "Super happy",
  "values": [
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7"
  ]
}
```

![](/README/happiness_0.png "segment_select example")

#### add_text_field 

```
{
  "id": "which_sports",
  "question": "Which sports do you like to play?",
  "question_type": "add_text_field",
  "input_type": "default",
  "show_if": {
     "id": "sports",
     "operation": "equals",
     "value": "Yes"
  }
}
```

