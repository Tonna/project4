package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.dao.SubjectDao;
import com.yakovchuk.sphinx.domain.Subject;

public class SubjectServiceImpl implements SubjectService {

    private SubjectDao subjectDao;

    @Override
    public Subject createSubject(String name, String languageCode) {
        assert (name != null);
        assert (!name.isEmpty());

        return subjectDao.create(name, languageCode);
    }

    public void setSubjectDao(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

}
