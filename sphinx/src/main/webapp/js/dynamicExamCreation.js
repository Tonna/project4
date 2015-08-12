function addQuestion(){
    var addQuestionHtml = document.getElementById('questionTemplate');
    var new_question=addQuestionHtml.cloneNode(true);
    new_question.id = "";
    new_question.style="inline";
    document.getElementById('questionsSection').appendChild(new_question);
}

function addAnswer(question){
    var addAnswerHtml = document.getElementById('answerTemplate');
    var answer=addAnswerHtml.cloneNode(true);
    answer.id="";
    answer.style="inline";
    var answersSection = question.getElementsByClassName("answersSection")[0];
    answersSection.appendChild(answer);
}
