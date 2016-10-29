package com.yakovchuk.project4.dao;

import com.yakovchuk.project4.domain.Subject;

import java.util.Collection;

public interface SubjectDao extends GenericDao<Subject> {
    Subject create(String name, String languageCode);

    Collection<Subject> getAll(String languageCode);
}
