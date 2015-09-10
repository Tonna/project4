package com.yakovchuk.sphinx.profile;

import java.util.Collection;
import java.util.Collections;

public class ProfileImpl implements Profile {

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
