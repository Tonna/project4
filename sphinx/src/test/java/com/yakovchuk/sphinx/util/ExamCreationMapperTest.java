package com.yakovchuk.sphinx.util;

import org.junit.Test;

import static com.yakovchuk.sphinx.dao.mock.ExamDataProvider.*;
import static org.junit.Assert.assertEquals;

public class ExamCreationMapperTest {

    @Test
    public void testCreationOfExamFromRequest() {
        assertEquals(getExam3OriginalWithoutIds(), new ExamCreationMapper().mapExam(getExam3FromCreationRequest()));
    }

}