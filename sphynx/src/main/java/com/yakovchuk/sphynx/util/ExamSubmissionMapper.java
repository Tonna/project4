package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

import java.util.Map;

public class ExamSubmissionMapper implements ExamMapper {

    @Override
    public Exam mapExam(Map<String, String[]> parameterMap) {
        return buildQuestions(parameterMap, new Exam.Builder().id(parameterMap.get("exam-id")[0]));
    }

    private Exam buildQuestions(Map<String, String[]> parameterMap, Exam.Builder builder) {
        parameterMap.keySet()
                .stream().filter(key -> key.contains("answer-id-for-question")).sorted().iterator()
                .forEachRemaining(q -> builder.addQuestion(buildQuestion(parameterMap, q)));
        return builder.build();
    }

    private Question buildQuestion(Map<String, String[]> parameterMap, String question) {
        String questionId = question.replace("answer-id-for-question-id-", "");
        Question.Builder builder = new Question.Builder().id(questionId);
        for (String answerId : parameterMap.get(question)) {
            builder.addAnswer(new Answer.Builder().id(answerId).build());
        }
        return builder.build();
    }
}
