package com.yakovchuk.sphinx.user;

import java.util.Collection;
import java.util.Collections;

public interface User {

    String getLogin();
    Collection<String> getRoles();
}
