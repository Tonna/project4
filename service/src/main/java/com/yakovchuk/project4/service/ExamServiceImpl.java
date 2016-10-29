package com.yakovchuk.project4.service;

import com.yakovchuk.project4.dao.ExamDao;
import com.yakovchuk.project4.domain.Exam;

import java.util.Collection;

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
    public Collection<Exam> getExamHeaders(String languageCode) {
        return examDao.getAllExamsWithoutQuestions(languageCode);
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
