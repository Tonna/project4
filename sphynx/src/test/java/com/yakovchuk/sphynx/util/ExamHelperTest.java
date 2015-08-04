package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.domain.Exam;
import org.junit.Test;

import java.util.ArrayList;

import static com.yakovchuk.sphynx.dao.ExamDataProvider.*;
import static org.junit.Assert.assertEquals;

public class ExamHelperTest {

    @Test
    public void testCreationOfExamFromRequest() {
        assertEquals(getExam3Answered(), new ExamHelper().createExamFromRequest(getExam3FromRequest()));
    }

    @Test
    public void testNoQuestionsInAnsweredExam() {
        assertEquals(0, new ExamHelper().checkExam(getExam1Original(), new Exam.Builder().id("1").build()));
    }

    @Test
    public void testQuestionsInAnsweredExamIsEmpty() {
        assertEquals(0, new ExamHelper().checkExam(getExam1Original(),
                new Exam.Builder().id("1").questions(new ArrayList<>()).build()));
    }

    @Test
    public void testAllQuestionsAnsweredCorrectly() {
        assertEquals(2, new ExamHelper().checkExam(getExam2Original(), getExam2Answered()));
    }


    @Test
    public void testAllQuestionsAnsweredWrong() {
        assertEquals(0, new ExamHelper().checkExam(getExam1Original(), getExam1Answered()));

    }


    @Test
    public void testOneQuestionAnsweredCorrectly() {
        assertEquals(1, new ExamHelper().checkExam(getExam3Original(), getExam3Answered()));
    }
}