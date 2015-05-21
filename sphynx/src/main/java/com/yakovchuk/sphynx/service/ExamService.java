package com.yakovchuk.sphynx.service;

import com.yakovchuk.sphynx.domain.Exam;

import java.util.Collection;

public interface ExamService {
    Exam getExam(String id);

    Collection<Exam> getExams();

    Exam createExam(Exam toCreate);
}
