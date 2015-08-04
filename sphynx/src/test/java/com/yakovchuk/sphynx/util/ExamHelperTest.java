package com.yakovchuk.sphynx.util;

import com.yakovchuk.sphynx.dao.ExamDataProvider;
import org.junit.Test;

import static com.yakovchuk.sphynx.dao.ExamDataProvider.*;
import static org.junit.Assert.assertEquals;

public class ExamHelperTest {

    @Test
    public void testCreationOfExamFromRequest() {
        assertEquals(getExam3Answered(), new ExamHelper().createExamFromRequest(getExam3FromRequest()));
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