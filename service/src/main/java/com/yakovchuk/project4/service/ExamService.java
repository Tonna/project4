package com.yakovchuk.project4.service;

import com.yakovchuk.project4.domain.Exam;

import java.util.Collection;

public interface ExamService {
    Exam getExam(String id);

    Collection<Exam> getExamHeaders(String languageCode);

    Collection<Exam> getExams();

    Exam createExam(Exam toCreate);
}
