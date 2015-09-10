package com.yakovchuk.sphinx.profile;

import java.util.Collection;

public interface Profile {

    String getLogin();
    Collection<String> getRoles();
}
