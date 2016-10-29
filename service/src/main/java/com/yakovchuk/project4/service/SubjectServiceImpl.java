package com.yakovchuk.project4.service;

import com.yakovchuk.project4.dao.SubjectDao;
import com.yakovchuk.project4.domain.Subject;

import java.util.Collection;

public class SubjectServiceImpl implements SubjectService {

    private SubjectDao subjectDao;

    @Override
    public Subject createSubject(String name, String languageCode) {
        assert (name != null);
        assert (!name.isEmpty());

        return subjectDao.create(name, languageCode);
    }

    @Override
    public Collection<Subject> getAll(String languageCode) {
        return subjectDao.getAll(languageCode);
    }

    @Override
    public Subject getSubject(String subjectName) {
        assert (subjectName != null);

        return subjectDao.get(subjectName);
    }

    public void setSubjectDao(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

}
