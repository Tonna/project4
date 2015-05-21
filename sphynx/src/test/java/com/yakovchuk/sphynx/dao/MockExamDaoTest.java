package com.yakovchuk.sphynx.dao;

import static com.yakovchuk.sphynx.dao.ExamDataProvider.getExam1;
import static com.yakovchuk.sphynx.dao.ExamDataProvider.getExam2;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.yakovchuk.sphynx.domain.Exam;

public class MockExamDaoTest {

    private MockExamDao dao;

    @Test
    public void testGetExamById() {
        dao = new MockExamDao(getExam1(), getExam2());
        assertEquals(getExam1(), dao.get("1"));
    }

    @Test
    public void testGetAllExams() {
        dao = new MockExamDao(getExam1(), getExam2());
        Collection<Exam> exams = dao.getAll();
        assertTrue(exams.contains(getExam1()));
        assertTrue(exams.contains(getExam2()));
    }

    @Test
    public void testCreateExam() {
        dao = new MockExamDao();
        assertTrue(dao.getAll().isEmpty());
        // assuming creation of new exam - without id
        Exam examToSave = new Exam.Builder(getExam1()).id(null).build();
        Exam persisted = dao.create(examToSave);
        assertNotNull(persisted.getId());
        assertEquals(examToSave.getName(), persisted.getName());
        assertEquals(examToSave.getSubject(), persisted.getSubject());
        assertEquals(examToSave.getQuestions(), persisted.getQuestions());

    }
}