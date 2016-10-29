package com.yakovchuk.project4.util;

import com.yakovchuk.project4.domain.Answer;
import com.yakovchuk.project4.domain.Exam;
import com.yakovchuk.project4.domain.Question;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        if (answeredExam.getQuestions() == null || answeredExam.getQuestions().isEmpty()) {
            return correctAnswers;
        }

        Map<String, Question> answeredQuestions = new HashMap<>();
        answeredExam.getQuestions().stream().forEach(q -> answeredQuestions.put(q.getId(), q));

        for (Question originalQuestion : originalExam.getQuestions()) {
            Question answeredQuestion = answeredQuestions.get(originalQuestion.getId());
            if (answeredQuestion != null && checkQuestion(originalQuestion, answeredQuestion)) {
                correctAnswers += 1;
            }
        }
        return correctAnswers;
    }

    /*
        check question
        get correct answers from original answer. get all answers from profile.
        if quantities match and each original answer has corresponding answer from profile input return true.
     */
    private boolean checkQuestion(Question originalQuestion, Question answeredQuestion) {

        Set<String> originalAnswerIds = new HashSet<>();
        originalQuestion.getAnswers().stream()
                .filter(Answer::getIsCorrect).map(Answer::getId)
                .iterator().forEachRemaining(originalAnswerIds::add);

        Set<String> answeredAnswerIds = new HashSet<>();
        answeredQuestion.getAnswers().stream().forEach(a -> answeredAnswerIds.add(a.getId()));

        return originalAnswerIds.equals(answeredAnswerIds);
    }
}
