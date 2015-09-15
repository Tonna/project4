package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.domain.Subject;

import java.util.Collection;

public interface SubjectService {

    Subject createSubject(String name, String languageCode);

    Collection<Subject> getAll(String languageCode);

    Subject getSubject(String subjectName);
}
