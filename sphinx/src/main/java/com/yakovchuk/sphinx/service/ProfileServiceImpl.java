package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.dao.ProfileDao;
import com.yakovchuk.sphinx.profile.Profile;

public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;

    public ProfileServiceImpl(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public Profile getProfile(String login, String password) {
        return profileDao.getProfile(login, password);
    }
}
