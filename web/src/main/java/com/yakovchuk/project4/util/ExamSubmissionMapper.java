package com.yakovchuk.project4.util;

import com.yakovchuk.project4.domain.Answer;
import com.yakovchuk.project4.domain.Exam;
import com.yakovchuk.project4.domain.Question;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ExamSubmissionMapper implements ExamMapper {

    @Override
    public Exam mapExam(Map<String, String[]> parameterMap) {
        return buildQuestions(parameterMap, new Exam.Builder().id(parameterMap.get("exam-id")[0]));
    }

    private Exam buildQuestions(Map<String, String[]> parameterMap, Exam.Builder builder) {
        parameterMap.keySet()
                .stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String key) {
                return key.contains("answer-id-for-question");
            }
        }).sorted().iterator()
                .forEachRemaining(new Consumer<String>() {
                    @Override
                    public void accept(String q) {
                        builder.addQuestion(ExamSubmissionMapper.this.buildQuestion(parameterMap, q));
                    }
                });
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
