package com.yakovchuk.project4.util;

import org.junit.Test;

import static com.yakovchuk.project4.dao.mock.ExamDataProvider.*;
import static org.junit.Assert.*;

public class ExamSubmissionMapperTest {

    @Test
    public void testCreationOfExamFromRequest() {
        assertEquals(getExam3OriginalWithoutIds(), new ExamCreationMapper().mapExam(getExam3FromCreationRequest()));
    }

}