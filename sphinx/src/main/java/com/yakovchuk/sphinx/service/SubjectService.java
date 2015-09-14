package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.domain.Subject;

public interface SubjectService {

    Subject createSubject(String name, String languageCode);
}
