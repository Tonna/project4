<div style="display: none;" id="questionFormHTML" class="development">
	<!-- TODO store value for validation? <input type="hidden" name="answerCount" value="0"/> -->
	<div class="development">
		<p>Text for question</p>
		<p>
			<textarea rows="2" name="question"/>
		</p>
	</div>
	<div class="development" id="addAnswerButton">
		<input type="button" value="Add answer" onclick="addAnswer(this.parentNode, this);return false;"/>
	</div>
</div>
