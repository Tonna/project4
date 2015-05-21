package com.yakovchuk.sphynx.service;

import java.util.Collection;

import com.yakovchuk.sphynx.dao.ExamDao;
import com.yakovchuk.sphynx.domain.Exam;

public class ExamServiceImpl implements ExamService {

    ExamDao examDao;

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
    public Collection<Exam> getExams() {
        //return map <Subject, Collection<Exam>>?
        return examDao.getAll();
    }

    @Override
    public Exam createExam(Exam toCreate) {
        return examDao.create(toCreate);
    }
}
