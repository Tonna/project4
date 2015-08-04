package com.yakovchuk.sphynx.dao;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

import java.util.HashMap;
import java.util.Map;

public class ExamDataProvider {

    public static Exam getExam1Original() {
        return new Exam.Builder().id("1").subject("Math").name("Basic math")
                .addQuestion(new Question.Builder().id("16").text("2 ^ 10 = ?")
                        .addAnswer(new Answer.Builder().text("1024").isCorrect(true).id("13").build())
                        .addAnswer(new Answer.Builder().text("20").isCorrect(false).id("14").build())
                        .build())
                .addQuestion(new Question.Builder().id("15").text("1 + 1 = ?")
                        .addAnswer(new Answer.Builder().text("2").isCorrect(true).id("11").build())
                        .addAnswer(new Answer.Builder().text("3").isCorrect(false).id("12").build())
                        .build())
                .build();
    }

    public static Exam getExam1Answered() {
        return new Exam.Builder().id("1")
                .addQuestion(new Question.Builder().id("16")
                        .addAnswer(new Answer.Builder().id("14").build())
                        .build())
                .addQuestion(new Question.Builder().id("15")
                        .addAnswer(new Answer.Builder().id("12").build())
                        .build())
                .build();
    }

    public static Exam getExam2Original() {
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

    public static Exam getExam2Answered() {
        return new Exam.Builder().id("2")
                .addQuestion(new Question.Builder().id("23")
                        .addAnswer(new Answer.Builder().id("21").build())
                        .build())
                .addQuestion(new Question.Builder().id("19")
                        .addAnswer(new Answer.Builder().id("18").build())
                        .build())
                .build();
    }

    public static Exam getExam3Original() {
        return new Exam.Builder().id("3").name("dummy exam").subject("development")
                .addQuestion(
                        new Question.Builder().id("11").text("first question of dummy exam")
                                .addAnswer(new Answer.Builder().id("111").text("first answer - wrong").build())
                                .addAnswer(new Answer.Builder().id("112").text("second answer - correct").isCorrect().build())
                                .addAnswer(new Answer.Builder().id("113").text("third answer - correct").isCorrect().build())
                                .addAnswer(new Answer.Builder().id("114").text("fourth answer - wrong").build())
                                .build())
                .addQuestion(
                        new Question.Builder().id("12").text("second question of dummy exam")
                                .addAnswer(new Answer.Builder().id("121").text("first answer - correct").isCorrect().build())
                                .addAnswer(new Answer.Builder().id("122").text("second answer -wrong").build())
                                .build())
                .addQuestion(
                        new Question.Builder().id("13").text("third question of dummy exam")
                                .addAnswer(new Answer.Builder().id("131").text("first answer - wrong").build())
                                .addAnswer(new Answer.Builder().id("132").text("second answer - correct").isCorrect().build())
                                .build())
                .build();
    }

    public static Exam getExam3Answered() {
        return new Exam.Builder().id("1")
                .addQuestion(
                        new Question.Builder().id("11")
                                .addAnswer(new Answer.Builder().id("111").build())
                                .addAnswer(new Answer.Builder().id("112").build())
                                .build())
                .addQuestion(
                        new Question.Builder().id("12")
                                .addAnswer(new Answer.Builder().id("121").build())
                                .build())
                .addQuestion(
                        new Question.Builder().id("13")
                                .addAnswer(new Answer.Builder().id("131").build())
                                .build())
                .build();
    }

    public static Map<String, String[]> getExam3FromRequest() {
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("exam-id", new String[]{"1"});
        map.put("answer-id-for-question-id-11", new String[]{"111", "112"});
        map.put("answer-id-for-question-id-12", new String[]{"121"});
        map.put("answer-id-for-question-id-13", new String[]{"131"});
        return map;
    }
}
