package com.yakovchuk.project4.dao;

import com.yakovchuk.project4.domain.Exam;

import java.util.Collection;

public interface ExamDao extends GenericDao<Exam> {
    Collection<Exam> getAllExamsWithoutQuestions(String languageCode);
}
