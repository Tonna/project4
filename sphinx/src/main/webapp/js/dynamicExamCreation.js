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
