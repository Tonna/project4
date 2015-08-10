<div style="display: none;" id="questionFormHTML">
    <!-- TODO store value for validation? <input type="hidden" name="answerCount" value="0"/> -->
    <div class="tableRow">
        <p>Text for question</p>
        <p><textarea rows="2" name="question"></textarea></p>
    </div>
    <input type="button" id="addAnswerButton" value="Add answer" onclick="addAnswer(this.parentNode, this);return false;">
</div>
