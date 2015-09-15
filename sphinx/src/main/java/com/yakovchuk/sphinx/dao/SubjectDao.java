package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.domain.Subject;

import java.util.Collection;

public interface SubjectDao extends GenericDao<Subject> {
    Subject create(String name, String languageCode);

    Collection<Subject> getAll(String languageCode);
}
