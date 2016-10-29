package com.yakovchuk.project4.service;

import com.yakovchuk.project4.profile.Profile;

public interface ProfileService {
    Profile getProfile(String login, String password);

    Profile changeLanguage(Profile profile, String newLanguage);
}
