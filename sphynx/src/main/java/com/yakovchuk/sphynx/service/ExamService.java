package com.yakovchuk.sphynx.service;

import com.yakovchuk.sphynx.domain.Exam;

import java.util.Collection;
import java.util.Map;

public interface ExamService {
    Exam getExam(String id);

    Map<String, Collection<Exam>> getExamsBySubject();

    Collection<Exam> getExams();

    Exam createExam(Exam toCreate);
}
