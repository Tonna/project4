function addQuestion(){
    var addQuestionHtml = document.getElementById('questionFormHTML').value;
    var new_question=document.createElement('div');
    new_question.className = "question";
    new_question.innerHTML=addQuestionHtml;
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
