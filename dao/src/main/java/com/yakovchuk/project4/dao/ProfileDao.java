package com.yakovchuk.project4.dao;

import com.yakovchuk.project4.profile.Profile;

public interface ProfileDao {
    Profile getProfile(String login, String password);

    Profile changeLanguage(Profile profile, String newLanguage);
}
