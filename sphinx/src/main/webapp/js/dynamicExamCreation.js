var questionCount = 0;
var answerCount = 0


function addQuestion(){
    var addQuestionHtml = document.getElementById('questionFormHTML');
    var new_question=addQuestionHtml.cloneNode(true);
    questionCount = questionCount + 1;
    new_question.id = "question" + questionCount;
    new_question.style="inline";
    new_question.className = "question";
    document.getElementById('questions').appendChild(new_question);
    document.getElementById('questions').appendChild(document.getElementById('addQuestionButton'));
}
function addAnswer(question, button){
    var addAnswerHtml = document.getElementById('answerFormHTML');
    var answer=addAnswerHtml.cloneNode(true);
    answerCount = answerCount + 1;
    answer.id="answerFormHTML" + answerCount;
    answer.style="inline";
    answer.className="answer tableRow";
    question.appendChild(answer);
    question.appendChild(button);
}
function init() {
    addQuestion()
}
window.onload = init;