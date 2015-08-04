package com.yakovchuk.sphynx.dao;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

public class ExamDataProvider {

    public static Exam getExam1() {
        return new Exam.Builder().id("1").subject("Math").name("Basic math")
                .addQuestion(new Question.Builder().id("16").text("2 ^ 10 = ?")
                        .addAnswer(new Answer.Builder().id("1024").isCorrect(true).id("13").build())
                        .addAnswer(new Answer.Builder().id("20").isCorrect(false).id("14").build())
                        .build())
                .addQuestion(new Question.Builder().id("15").text("1 + 1 = ?")
                        .addAnswer(new Answer.Builder().id("2").isCorrect(true).id("11").build())
                        .addAnswer(new Answer.Builder().id("3").isCorrect(false).id("12").build())
                        .build())
                .build();
    }

    public static Exam getExam2() {
        return new Exam.Builder().name("Despair exam").id("2").subject("Software development")
                .addQuestion(new Question.Builder().text("Maybe I should type less? Is there a way to develop software fast?").id("23")
                        .addAnswer(new Answer.Builder().text("No").isCorrect(false).id("22").build())
                        .addAnswer(new Answer.Builder().text("Yes").isCorrect(true).id("21").build())
                        .build())
                .addQuestion(new Question.Builder().text("Is it enough boilerplate code?").id("19")
                        .addAnswer(new Answer.Builder().text("Yes").isCorrect(false).id("17").build())
                        .addAnswer(new Answer.Builder().text("No").isCorrect(true).id("18").build())
                        .build())
                .build();
    }
}
