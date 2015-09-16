package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.domain.Exam;

import java.util.Collection;

public interface ExamService {
    Exam getExam(String id);

    Collection<Exam> getExamHeaders(String languageCode);

    Collection<Exam> getExams();

    Exam createExam(Exam toCreate);
}
