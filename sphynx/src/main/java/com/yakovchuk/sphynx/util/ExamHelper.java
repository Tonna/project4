package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

import java.util.Map;

public class ExamHelper {
    public Exam createExamFromRequest(Map<String, String[]> parameterMap) {
        return buildQuestions(parameterMap, new Exam.Builder().id(parameterMap.get("exam-id")[0]));
    }

    private Exam buildQuestions(Map<String, String[]> parameterMap, Exam.Builder builder) {
        parameterMap.keySet()
                .stream().filter(key -> key.contains("answer-id-for-question")).iterator().
                forEachRemaining(q -> builder.addQuestion(buildQuestion(parameterMap, q)));
        return builder.build();
    }

    private Question buildQuestion(Map<String, String[]> parameterMap, String question) {
        Question.Builder builder = new Question.Builder().id(question.replace("answer-id-for-question-id-", ""));
        for (String answerId : parameterMap.get(question)) {
            builder.addAnswer(new Answer.Builder().id(answerId).build());
        }
        return builder.build();
    }
}