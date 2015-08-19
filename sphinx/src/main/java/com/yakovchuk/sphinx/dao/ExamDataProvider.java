package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Answer;
import com.yakovchuk.sphinx.domain.Exam;
import com.yakovchuk.sphinx.domain.Question;

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

    public static Exam getExam3OriginalWithoutIds() {
        return new Exam.Builder().name("dummy exam").subject("development")
                .addQuestion(
                        new Question.Builder().text("first question of dummy exam")
                                .addAnswer(new Answer.Builder().text("first answer - wrong").build())
                                .addAnswer(new Answer.Builder().text("second answer - correct").isCorrect().build())
                                .addAnswer(new Answer.Builder().text("third answer - correct").isCorrect().build())
                                .addAnswer(new Answer.Builder().text("fourth answer - wrong").build())
                                .build())
                .addQuestion(
                        new Question.Builder().text("second question of dummy exam")
                                .addAnswer(new Answer.Builder().text("first answer - correct").isCorrect().build())
                                .addAnswer(new Answer.Builder().text("second answer - wrong").build())
                                .build())
                .addQuestion(
                        new Question.Builder().text("third question of dummy exam")
                                .addAnswer(new Answer.Builder().text("first answer - wrong").build())
                                .addAnswer(new Answer.Builder().text("second answer - correct").isCorrect().build())
                                .build())
                .build();
    }

    public static Map<String, String[]> getExam3FromCreationRequest() {
        Map<String, String[]> map = new HashMap<>();

        map.put("subject", new String[]{"development"});
        map.put("examName", new String[]{"dummy exam"});

        map.put("questionCount-1-questionText", new String[]{"first question of dummy exam"});
        map.put("questionCount-1-answerCount-1-answerText", new String[]{"first answer - wrong"});
        map.put("questionCount-1-answerCount-2-answerText", new String[]{"second answer - correct"});
        map.put("questionCount-1-answerCount-2-isCorrect", new String[]{"true"});
        map.put("questionCount-1-answerCount-3-answerText", new String[]{"third answer - correct"});
        map.put("questionCount-1-answerCount-3-isCorrect", new String[]{"true"});
        map.put("questionCount-1-answerCount-4-answerText", new String[]{"fourth answer - wrong"});

        map.put("questionCount-2-questionText", new String[]{"second question of dummy exam"});
        map.put("questionCount-2-answerCount-5-answerText", new String[]{"first answer - correct"});
        map.put("questionCount-2-answerCount-5-isCorrect", new String[]{"true"});
        map.put("questionCount-2-answerCount-6-answerText", new String[]{"second answer - wrong"});

        map.put("questionCount-3-questionText", new String[]{"third question of dummy exam"});
        map.put("questionCount-3-answerCount-7-answerText", new String[]{"first answer - wrong"});
        map.put("questionCount-3-answerCount-8-answerText", new String[]{"second answer - correct"});
        map.put("questionCount-3-answerCount-8-isCorrect", new String[]{"true"});

        return map;
    }

    public static Exam getExam3Answered() {
        return new Exam.Builder().id("3")
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

    public static Map<String, String[]> getExam3FromSubmissionRequest() {
        Map<String, String[]> map = new HashMap<>();
        map.put("exam-id", new String[]{"3"});
        map.put("answer-id-for-question-id-11", new String[]{"111", "112"});
        map.put("answer-id-for-question-id-12", new String[]{"121"});
        map.put("answer-id-for-question-id-13", new String[]{"131"});
        return map;
    }
}
