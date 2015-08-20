package com.yakovchuk.sphinx.service;

import com.yakovchuk.sphinx.user.User;

public interface UserService {
    User getUser(String login, String password);
}
