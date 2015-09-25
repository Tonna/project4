package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.profile.Profile;

public interface ProfileDao {
    Profile getProfile(String login, String password);

    Profile changeLanguage(Profile profile, String newLanguage);
}
