package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Subject;

public interface SubjectDao extends GenericDao<Subject> {
    Subject create(String name, String languageCode);
}
