package com.yakovchuk.project4.profile;

import java.util.Collection;

public abstract class Profile {

    public abstract String getLogin();
    public abstract Collection<String> getRoles();
    public abstract String getLanguageCode();
}
