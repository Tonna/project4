package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.domain.Exam;

import java.util.Collection;
import java.util.Map;

public interface ExamService {
    Exam getExam(String id);

    Collection<Exam> getExamHeaders();

    Collection<Exam> getExams();

    Exam createExam(Exam toCreate);
}
