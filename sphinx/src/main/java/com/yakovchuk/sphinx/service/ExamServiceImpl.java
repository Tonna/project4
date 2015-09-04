package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.dao.ExamDao;
import com.yakovchuk.sphinx.domain.Exam;

import java.util.*;

public class ExamServiceImpl implements ExamService {

    private ExamDao examDao;

    public ExamDao getExamDao() {
        return examDao;
    }

    public void setExamDao(ExamDao examDao) {
        this.examDao = examDao;
    }

    @Override
    public Exam getExam(String id) {
        return examDao.get(id);
    }

    @Override
    public Collection<Exam> getExamHeaders() {
        return examDao.getAllHeaders();
    }

    @Override
    public Collection<Exam> getExams() {
        return examDao.getAll();
    }

    @Override
    public Exam createExam(Exam toCreate) {
        return examDao.create(toCreate);
    }
}
