package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.profile.Profile;

public interface ProfileService {
    Profile getProfile(String login, String password);
}
