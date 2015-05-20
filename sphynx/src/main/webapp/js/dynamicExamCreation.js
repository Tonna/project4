function addQuestion(){
    var addQuestionHtml = document.getElementById('questionFormHTML');
    var new_question=addQuestionHtml.cloneNode(true);
    new_question.id = "";
    new_question.style="inline";
    new_question.className = "question";
    document.getElementById('questions').appendChild(new_question);
    document.getElementById('questions').appendChild(document.getElementById('addQuestionButton'));
}
function addAnswer(question, button){
    var addAnswerHtml = document.getElementById('answerFormHTML');
    var answer=addAnswerHtml.cloneNode(true);
    answer.id="";
    answer.style="inline";
    answer.className="answer tableRow";
    question.appendChild(answer);
    question.appendChild(button);
}
