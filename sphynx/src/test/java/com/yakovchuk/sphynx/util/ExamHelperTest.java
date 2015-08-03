package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExamHelperTest {

    @Test
    public void testCreationOfExamFromRequest() {
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("exam-id", new String[]{"23"});
        map.put("answer-id-for-question-id-1", new String[]{"11", "12"});
        map.put("answer-id-for-question-id-2", new String[]{"21"});
        map.put("answer-id-for-question-id-3", new String[]{"31"});
        Exam resultExam = new ExamHelper().createExamFromRequest(map);
        Exam expectedExam = new Exam.Builder().id("23")
                .addQuestion(
                        new Question.Builder().id("1")
                                .addAnswer(new Answer.Builder().id("11").build())
                                .addAnswer(new Answer.Builder().id("12").build()).build())
                .addQuestion(
                        new Question.Builder().id("2")
                                .addAnswer(new Answer.Builder().id("21").build()).build())
                .addQuestion(
                        new Question.Builder().id("3")
                                .addAnswer(new Answer.Builder().id("31").build()).build())
                .build();
        assertEquals(expectedExam, resultExam);
    }

}