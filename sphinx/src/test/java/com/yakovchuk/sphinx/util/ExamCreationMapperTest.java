package com.yakovchuk.sphinx.util;

import org.junit.Test;

import static com.yakovchuk.sphinx.dao.ExamDataProvider.getExam3Answered;
import static com.yakovchuk.sphinx.dao.ExamDataProvider.getExam3FromSubmissionRequest;
import static org.junit.Assert.assertEquals;

public class ExamCreationMapperTest {

    @Test
    public void testCreationOfExamFromRequest() {
        assertEquals(getExam3Answered(), new ExamSubmissionMapper().mapExam(getExam3FromSubmissionRequest()));
    }

}