var questionCount = 0;
var answerCount = 0;

function addQuestion(){
    questionCount = questionCount + 1;
    var new_question =
        $("#questionTemplate").clone().removeAttr("id").removeAttr("style").attr("questionCount", questionCount);
    $(new_question).find("p textarea").attr("name", "questionCount-" + questionCount + "-questionText");
    $("#questionsSection").append(new_question);
    addAnswer(new_question)
    addAnswer(new_question);
}

function addAnswer(question){
    answerCount = answerCount + 1;
    var new_answer =
        $("#answerTemplate").clone().removeAttr("id").removeAttr("style").attr("answerCount", answerCount);
    $(new_answer).find("input[name='answer']").attr("name", "questionCount-" + $(question).attr("questionCount") + "-answerCount-" + answerCount + "-answerText");
    $(new_answer).find("input[name='isCorrect']").attr("name", "questionCount-" + $(question).attr("questionCount") + "-answerCount-" + answerCount + "-isCorrect");
    $(question).find(".answersSection").append(new_answer);
}

function validateExam(){
    try{
    var  isSubjectValid = validateSubject();
    var  isExamNameValid = validateExamName();
    var  isQuestionsValid = validateQuestions();
    var isExamValid = isSubjectValid && isExamNameValid && isQuestionsValid;
    if(isExamValid){
        $("#errorMessage").attr("style", "display: none;");
    } else {
        $("#errorMessage").removeAttr("style");
        window.scrollTo(0, 0);
    }
    return isExamValid ;
    } catch(err){
    return false;
    }
}

function getTextField(fieldName){
    return $("input[name='" + fieldName + "']");
}

function validateQuestions(){
    var questionsValid = true;
    var questions = $("#questionsSection").children('div.question');
    for(var i = 0; i < questions.length; i++){
        questionsValid = Boolean(questionsValid & validateQuestion(questions[i]));
    }
    return questionsValid;
}

function validateQuestion(question){
    var textFormIsEmpty = $(question).find('textarea').prop('value') == '';
    var result = true;
    if(textFormIsEmpty){
        $(question).find('textarea').addClass("validationError");
        result = false;
    } else {
         $(question).find('textarea').removeClass("validationError");
    }
    return result & validateAnswers(question);
    
}

function validateAnswers(question){
    var answers = $(question).find(".answersSection .answer");
    var atLeastOneAnswerIsCorrect = false;
    var allAnswersValid = true;
    for(var i = 0; i < answers.length; i++){
        atLeastOneAnswerIsCorrect = Boolean(atLeastOneAnswerIsCorrect | isAnswerMarkedAsCorrect(answers[i]));
        allAnswersValid = Boolean(allAnswersValid & validateAnswer(answers[i]));
    }
    if(!atLeastOneAnswerIsCorrect){
        for(var i = 0; i < answers.length; i++){
            $(answers[i]).find("input[type='checkbox']").addClass("validationError");
        }
    } else {
        for(var i = 0; i < answers.length; i++){
            $(answers[i]).find("input[type='checkbox']").removeClass("validationError");
        }
    }
    return atLeastOneAnswerIsCorrect & allAnswersValid;
}

function isAnswerMarkedAsCorrect(answer){
    var checkbox = $(answer).find("input[type='checkbox']");
    var isChecked = checkbox.prop("checked");
    return isChecked;
}

function validateAnswer(answer){
    var textField = $(answer).find("input[type='text']");
    if(textField.prop('value').trim() == ''){
        textField.addClass("validationError");
        return false;
    }
    textField.removeClass("validationError");
    return true;
}

function validateSubject(){
    var subject = getTextField("subject");
    if(subject.prop('value').trim() ==  ''){
        subject.addClass("validationError");
        return false;
    }
    subject.removeClass("validationError");
    return true;
}

function validateExamName(){
    var examName = getTextField("examName");
    if(examName.prop('value').trim() ==  ''){
        examName.addClass("validationError");
        return false;
    }
    examName.removeClass("validationError");
    return true;
}