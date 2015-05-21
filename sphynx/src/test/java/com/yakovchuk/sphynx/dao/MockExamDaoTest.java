package com.yakovchuk.sphynx.dao;

import static com.yakovchuk.sphynx.dao.ExamDataProvider.*;
import static com.yakovchuk.sphynx.dao.ExamDataProvider.getExam1;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.yakovchuk.sphynx.domain.Answer;
import com.yakovchuk.sphynx.domain.Exam;
import com.yakovchuk.sphynx.domain.Question;

public class MockExamDaoTest {

    private MockExamDao dao;

    @Before
    public void init() {
        dao = new MockExamDao();
    }

    // get | put couple of exams to dao and get some exact one by id

    @Test
    public void testGetExamById() {

        dao.getData().add(getExam1());
        dao.getData().add(getExam2());

        assertEquals(getExam1(), dao.get("1"));
    }

    // get | put in empty dao two exams -> getAll and get those two exams back

    // create | add exam

    // update | get one exam, make changes to it, call update and get it again

    // delete | remove exam by id

}