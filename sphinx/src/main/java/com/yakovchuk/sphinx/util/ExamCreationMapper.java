package com.yakovchuk.sphinx.util;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;
import com.yakovchuk.sphinx.domain.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ExamCreationMapper implements ExamMapper {

    @Override
    public Exam mapExam(Map<String, String[]> parameterMap) {
        return buildQuestions(parameterMap,
                new Exam.Builder()
                        .name(parameterMap.get("examName")[0])
                        .subject(new Subject.Builder().name(parameterMap.get("subject")[0]).build()))
                .build();
    }

    private Exam.Builder buildQuestions(Map<String, String[]> parameterMap, Exam.Builder builder) {
        //separate different questions
        Map<String, Map<String, String>> questionsMap = separateByQuestions(parameterMap);

        for (Map.Entry<String, Map<String, String>> entry : questionsMap.entrySet()) {
            Map<String, String> question = entry.getValue();
            String questionText = null;
            for (Map.Entry<String, String> qEntry : question.entrySet()) {
                if(qEntry.getKey().contains("questionText")){
                    questionText = qEntry.getValue();
                }
            }

            builder.addQuestion(new Question.Builder().text(questionText).answers(buildAnswers(entry.getValue())).build());
        }


        return builder;
    }

    private Collection<Answer> buildAnswers(Map<String, String> questionData) {
        ArrayList<Answer> answers = new ArrayList<>();
        Map<String, Map<String, String>> separateByAnswers = separateByAnswers(questionData);

        for (Map.Entry<String, Map<String, String>> entry : separateByAnswers.entrySet()) {
            Answer.Builder answer = new Answer.Builder().text(entry.getValue().get("answerText"));
            if (entry.getValue().get("isCorrect") != null) {
                answer.isCorrect();
            }
            answers.add(answer.build());
        }
        return answers;


    }

    private Map<String, Map<String, String>> separateByAnswers(Map<String, String> parameterMap) {
        Map<String, Map<String, String>> answersMap = new HashMap<>();
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            if (entry.getKey().contains("answerCount")) {
                String answerCount = entry.getKey().split("-")[3];
                if (!answersMap.containsKey(answerCount)) {
                    answersMap.put(answerCount, new HashMap<>());
                }
                answersMap.get(answerCount).put(entry.getKey().split("-")[4], entry.getValue());
            }
        }
        return answersMap;
    }

    private Map<String, Map<String, String>> separateByQuestions(Map<String, String[]> parameterMap) {
        Map<String, Map<String, String>> examMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getKey().contains("questionCount")) {
                String questionCount = entry.getKey().split("-")[1];
                if (!examMap.containsKey(questionCount)) {
                    examMap.put(questionCount, new HashMap<>());
                }
                examMap.get(questionCount).put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return examMap;
    }
}
