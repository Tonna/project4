package com.yakovchuk.sphynx.dao;

import java.util.ArrayList;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

public class ExamDataProvider {

    public static Exam getExam1() {
        ArrayList<Question> questions1 = new ArrayList<Question>();

        ArrayList<Answer> answers11 = new ArrayList<Answer>();
        answers11.add(new Answer.Builder().id("2").isCorrect(true).id("11").build());
        answers11.add(new Answer.Builder().id("3").isCorrect(false).id("12").build());
        questions1.add(new Question.Builder("1 + 1 = ?", answers11).id("15").build());

        ArrayList<Answer> answers12 = new ArrayList<Answer>();
        answers12.add(new Answer.Builder().id("1024").isCorrect(true).id("13").build());
        answers12.add(new Answer.Builder().id("20").isCorrect(false).id("14").build());
        questions1.add(new Question.Builder("2 ^ 10 = ?", answers12).id("16").build());

        return new Exam.Builder().name("Basic math").questions(questions1).id("1").subject("Math").build();
    }

    public static Exam getExam2() {
        ArrayList<Question> questions2 = new ArrayList<Question>();

        ArrayList<Answer> answers21 = new ArrayList<Answer>();
        answers21.add(new Answer.Builder().text("Yes").isCorrect(false).id("17").build());
        answers21.add(new Answer.Builder().text("No").isCorrect(true).id("18").build());
        questions2.add(new Question.Builder("Is it enough boilerplate code?", answers21).id("19").build());

        ArrayList<Answer> answers22 = new ArrayList<Answer>();
        answers22.add(new Answer.Builder().text("Yes").isCorrect(true).id("21").build());
        answers22.add(new Answer.Builder().text("No").isCorrect(false).id("22").build());
        questions2.add(new Question.Builder("Maybe I should type less? Is there a way to develop software fast?",
                answers22).id("23").build());

        return new Exam.Builder().name("Despair exam").questions(questions2).id("2").subject("Software development").build();
    }
}
