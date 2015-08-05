package com.yakovchuk.sphinx.util;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;

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

        Map<String, Question> userAnsweredQuestions = new HashMap<>();
        answeredExam.getQuestions().stream().forEach(q -> userAnsweredQuestions.put(q.getId(), q));

        for (Question originalQuestion : originalExam.getQuestions()) {
            Question userQuestion = userAnsweredQuestions.get(originalQuestion.getId());
            if (userQuestion != null && checkQuestion(originalQuestion, userQuestion)) {
                correctAnswers += 1;
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

        Set<String> originalAnswerIds = new HashSet<>();
        originalQuestion.getAnswers().stream()
                .filter(Answer::getIsCorrect).map(Answer::getId)
                .iterator().forEachRemaining(id -> originalAnswerIds.add(id));

        Set<String> userAnswerIds = new HashSet<>();
        answeredQuestion.getAnswers().stream().forEach(a -> userAnswerIds.add(a.getId()));

        return originalAnswerIds.equals(userAnswerIds);
    }
}
