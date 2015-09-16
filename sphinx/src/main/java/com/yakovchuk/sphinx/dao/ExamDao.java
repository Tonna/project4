package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Exam;

import java.util.Collection;

public interface ExamDao extends GenericDao<Exam> {
    Collection<Exam> getAllExamsWithoutQuestions(String languageCode);
}
