package com.yakovchuk.project4.service;

import com.yakovchuk.project4.domain.Subject;

import java.util.Collection;

public interface SubjectService {

    Subject createSubject(String name, String languageCode);

    Collection<Subject> getAll(String languageCode);

    Subject getSubject(String subjectName);
}
