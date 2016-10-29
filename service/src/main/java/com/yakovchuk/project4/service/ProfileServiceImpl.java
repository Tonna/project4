package com.yakovchuk.project4.service;

import com.yakovchuk.project4.dao.ProfileDao;
import com.yakovchuk.project4.profile.Profile;

public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;

    public ProfileServiceImpl(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public Profile getProfile(String login, String password) {
        return profileDao.getProfile(login, password);
    }

    @Override
    public Profile changeLanguage(Profile profile, String newLanguage) {
        //FIXME if not expected language passes it can cause an exception
        //that will be visible to user
        return profileDao.changeLanguage(profile, newLanguage);
    }
}
