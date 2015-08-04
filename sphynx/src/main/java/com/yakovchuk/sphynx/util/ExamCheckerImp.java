package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ExamCheckerImp implements ExamChecker {

    /*
        check exam
        take original and answered exam.
        take first question from original, find corresponding question in answered exam, if found check that question.
        If question answered correctly correctAnswers =+ 1
    */
    public int checkExam(Exam originalExam, Exam answeredExam) {
        assert (originalExam.getId().equals(answeredExam.getId()));

        int correctAnswers = 0;

        if(answeredExam.getQuestions() == null || answeredExam.getQuestions().isEmpty()){
            return correctAnswers;
        }

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
