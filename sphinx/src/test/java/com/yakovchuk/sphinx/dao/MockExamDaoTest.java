package com.yakovchuk.sphinx.dao;

import static com.yakovchuk.sphinx.dao.mock.ExamDataProvider.getExam1Original;
import static com.yakovchuk.sphinx.dao.mock.ExamDataProvider.getExam2Original;
import static org.junit.Assert.*;

import java.util.Collection;

import com.yakovchuk.sphinx.dao.mock.MockExamDao;
import org.junit.Test;

import com.yakovchuk.sphinx.domain.Exam;

public class MockExamDaoTest {

    private MockExamDao dao;

    @Test
    public void testGetExamById() {
        dao = new MockExamDao(getExam1Original(), getExam2Original());
        assertEquals(getExam1Original(), dao.get("1"));
    }

    @Test
    public void testGetAllExams() {
        dao = new MockExamDao(getExam1Original(), getExam2Original());
        Collection<Exam> exams = dao.getAll();
        assertTrue(exams.contains(getExam1Original()));
        assertTrue(exams.contains(getExam2Original()));
    }

    @Test
    public void testCreateExam() {
        dao = new MockExamDao();
        assertTrue(dao.getAll().isEmpty());
        // assuming creation of new exam - without id
        Exam examToSave = new Exam.Builder(getExam1Original()).id(null).build();
        Exam persisted = dao.create(examToSave);
        assertNotNull(persisted.getId());
        assertEquals(examToSave.getName(), persisted.getName());
        assertEquals(examToSave.getSubject(), persisted.getSubject());
        assertEquals(examToSave.getQuestions(), persisted.getQuestions());

    }
}