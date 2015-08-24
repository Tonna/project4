package com.yakovchuk.sphinx.user;

import java.util.Collection;
import java.util.Collections;

public class NullUser implements User {

    private static final NullUser nullUser = new NullUser();

    private NullUser() {

    }

    public static NullUser getInstance(){
        return nullUser;
    }

    @Override
    public String getLogin() {
        return "Null User";
    }

    @Override
    public Collection<String> getRoles() {
        return Collections.EMPTY_LIST;
    }
}
