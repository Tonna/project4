var questionCount = 0;
var answerCount = 0


function addQuestion(){
    var addQuestionHtml = document.getElementById('questionFormHTML');
    var new_question=addQuestionHtml.cloneNode(true);
    questionCount = questionCount + 1;
    new_question.id = "question" + questionCount;
    new_question.style="inline";
    new_question.className = "question development";
    document.getElementById('questions').appendChild(new_question);
    document.getElementById('questions').appendChild(document.getElementById('addQuestionButton'));
}
function addAnswer(question, button){
//TODO button shouldn't be extracted from div. It breaks markup
    var addAnswerHtml = document.getElementById('answerFormHTML');
    var answer=addAnswerHtml.cloneNode(true);
    answerCount = answerCount + 1;
    answer.id="answerFormHTML" + answerCount;
    answer.style="inline";
    answer.className="answer development";
    question.appendChild(answer);
    question.appendChild(button);
}
function init() {
//TODO call from jsp?
    addQuestion()
}
window.onload = init;