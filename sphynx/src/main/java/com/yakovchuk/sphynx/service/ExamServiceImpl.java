package com.yakovchuk.sphynx.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
    public Map<String, Collection<Exam>> getExamsBySubject() {
        // return map <Subject, Collection<Exam>>?
        Map<String, Collection<Exam>> subjectAndItsExams = new TreeMap<String, Collection<Exam>>();

        Collection<Exam> exams = examDao.getAll();
        for (Exam exam : exams) {
            if (subjectAndItsExams.containsKey(exam.getSubject())) {
                subjectAndItsExams.get(exam.getSubject()).add(exam);
            } else {
                Set<Exam> subjEx = new HashSet<Exam>();
                subjEx.add(exam);
                subjectAndItsExams.put(exam.getSubject(), subjEx);
            }
        }

        return subjectAndItsExams;
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
