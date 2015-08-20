package com.yakovchuk.sphinx.dao;

import com.yakovchuk.sphinx.user.User;

public interface UserDao {
    User getUser(String login, String password);
}
