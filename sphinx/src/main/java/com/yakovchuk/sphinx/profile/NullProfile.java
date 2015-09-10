package com.yakovchuk.sphinx.profile;

import java.util.Collection;
import java.util.Collections;

public class NullProfile implements Profile {

    private static final NullProfile NULL_PROFILE = new NullProfile();

    private NullProfile() {

    }

    public static NullProfile getInstance(){
        return NULL_PROFILE;
    }

    @Override
    public String getLogin() {
        return "Null Profile";
    }

    @Override
    public Collection<String> getRoles() {
        return Collections.EMPTY_LIST;
    }
}