package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ExamHelper {

    public Exam createExamFromRequest(Map<String, String[]> parameterMap) {
        return buildQuestions(parameterMap, new Exam.Builder().id(parameterMap.get("exam-id")[0]));
    }

    private Exam buildQuestions(Map<String, String[]> parameterMap, Exam.Builder builder) {
        parameterMap.keySet()
                .stream().filter(key -> key.contains("answer-id-for-question")).sorted().iterator()
                .forEachRemaining(q -> builder.addQuestion(buildQuestion(parameterMap, q)));
        return builder.build();
    }

    private Question buildQuestion(Map<String, String[]> parameterMap, String question) {
        Question.Builder builder = new Question.Builder().id(question.replace("answer-id-for-question-id-", ""));
        for (String answerId : parameterMap.get(question)) {
            builder.addAnswer(new Answer.Builder().id(answerId).build());
        }
        return builder.build();
    }

    /*
        check exam
        take original and answered exam.
        take first question from original, find corresponding question in answered exam, if found check that question.
        If question answered correctly correctAnswers =+ 1
    */
    public int checkExam(Exam originalExam, Exam answeredExam) {
        assert (originalExam.getId().equals(answeredExam.getId()));

        int correctAnswers = 0;

        for (Question originalQuestion : originalExam.getQuestions()) {
            for (Question answeredQuestion : answeredExam.getQuestions()) {
                if (originalQuestion.getId().equals(answeredQuestion.getId())) {
                    if (checkQuestion(originalQuestion, answeredQuestion)) {
                        correctAnswers += 1;
                    }
                }
            }

        }

        return correctAnswers;


    }

    /*
        check question
        get correct answers from original answer. get all answers from user.
        if quantities match and each original answer has corresponding answer from user input return true.
     */
    private boolean checkQuestion(Question originalQuestion, Question answeredQuestion) {
        Stream<Answer> originalAnswers = originalQuestion.getAnswers().stream().filter(a -> a.getIsCorrect());

        if (originalQuestion.getAnswers().stream().filter(a -> a.getIsCorrect()).count() == answeredQuestion.getAnswers().size()) {
            Function<Answer, Boolean> ggg = oA -> {
                Predicate<Answer> isAnswerIdsEqual = uA -> uA.getId().equals(oA.getId());
                return answeredQuestion.getAnswers().stream().anyMatch(isAnswerIdsEqual);
            };
            Stream<Boolean> booleanStream = originalAnswers.map(ggg);
            return booleanStream.allMatch(b -> b);
        }


        return false;
    }
}
