package com.yakovchuk.sphinx.user;

import java.util.Collection;
import java.util.Collections;

public class UserImpl implements User{

    private String login;
    private Collection<String> roles = Collections.EMPTY_LIST;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public Collection<String> getRoles() {
        return roles;
    }
}
