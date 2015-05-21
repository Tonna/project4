package com.yakovchuk.sphynx.dao;

import java.util.ArrayList;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

public class ExamDataProvider {

    public static Exam getExam1() {
        ArrayList<Question> questions1 = new ArrayList<Question>();

        ArrayList<Answer> answers11 = new ArrayList<Answer>();
        answers11.add(new Answer.Builder("2", true).build());
        answers11.add(new Answer.Builder("3", false).build());
        questions1.add(new Question.Builder("1 + 1 = ?", answers11).build());

        ArrayList<Answer> answers12 = new ArrayList<Answer>();
        answers12.add(new Answer.Builder("1024", true).build());
        answers12.add(new Answer.Builder("20", false).build());
        questions1.add(new Question.Builder("2 ^ 10 = ?", answers12).build());

        return new Exam.Builder("Math exam", questions1).id("1").build();
    }

    public static Exam getExam2() {
        ArrayList<Question> questions2 = new ArrayList<Question>();

        ArrayList<Answer> answers21 = new ArrayList<Answer>();
        answers21.add(new Answer.Builder("Yes", false).build());
        answers21.add(new Answer.Builder("No", true).build());
        questions2.add(new Question.Builder("Is it enough boilerplate code?", answers21).build());

        ArrayList<Answer> answers22 = new ArrayList<Answer>();
        answers22.add(new Answer.Builder("Yes", true).build());
        answers22.add(new Answer.Builder("No", false).build());
        questions2.add(new Question.Builder("Maybe I should type less? Is there a way to develop software fast?",
                answers22).build());

        return new Exam.Builder("Despair exam", questions2).id("2").build();
    }
}
