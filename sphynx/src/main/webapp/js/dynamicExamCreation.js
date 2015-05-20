function addQuestion(){
    var addQuestionHtml = document.getElementById('questionFormHTML').value;
    var new_question=document.createElement('div');
    new_question.className = "question";
    new_question.innerHTML=addQuestionHtml;
    document.getElementById('questions').appendChild(new_question);
    document.getElementById('questions').appendChild(document.getElementById('addQuestionButton'));
}
function addAnswer(question, button){
    var addAnswerHtml = document.getElementById('answerFormHTML').value;
    var answer=document.createElement('div');
    answer.className="answer tableRow";
    answer.innerHTML=addAnswerHtml;
    question.appendChild(answer);
    question.appendChild(button);
}
