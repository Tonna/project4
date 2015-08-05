package com.yakovchuk.sphinx.util;

import org.junit.Test;

import static com.yakovchuk.sphinx.dao.ExamDataProvider.getExam3Answered;
import static com.yakovchuk.sphinx.dao.ExamDataProvider.getExam3FromRequest;
import static org.junit.Assert.*;

public class ExamSubmissionMapperTest {

    @Test
    public void testCreationOfExamFromRequest() {
        assertEquals(getExam3Answered(), new ExamSubmissionMapper().mapExam(getExam3FromRequest()));
    }

}