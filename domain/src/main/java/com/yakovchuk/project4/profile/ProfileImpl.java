package com.yakovchuk.project4.profile;

import java.util.Collection;
import java.util.Collections;

public class ProfileImpl extends Profile {

    private String login;
    private Collection<String> roles = Collections.EMPTY_LIST;
    private String languageCode;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
