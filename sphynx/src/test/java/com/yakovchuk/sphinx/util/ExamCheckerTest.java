package com.yakovchuk.sphinx.util;

import com.yakovchuk.sphinx.domain.Exam;
import org.junit.Test;

import java.util.ArrayList;

import static com.yakovchuk.sphinx.dao.ExamDataProvider.*;
import static org.junit.Assert.assertEquals;

public class ExamCheckerTest {

    private final ExamCheckerImp examChecker = new ExamCheckerImp();

    @Test
    public void testNoQuestionsInAnsweredExam() {
        assertEquals(0, examChecker.checkExam(getExam1Original(), new Exam.Builder().id("1").build()));
    }

    @Test
    public void testQuestionsInAnsweredExamIsEmpty() {
        assertEquals(0, examChecker.checkExam(getExam1Original(),
                new Exam.Builder().id("1").questions(new ArrayList<>()).build()));
    }

    @Test
    public void testAllQuestionsAnsweredCorrectly() {
        assertEquals(2, examChecker.checkExam(getExam2Original(), getExam2Answered()));
    }


    @Test
    public void testAllQuestionsAnsweredWrong() {
        assertEquals(0, examChecker.checkExam(getExam1Original(), getExam1Answered()));

    }


    @Test
    public void testOneQuestionAnsweredCorrectly() {
        assertEquals(1, examChecker.checkExam(getExam3Original(), getExam3Answered()));
    }
}