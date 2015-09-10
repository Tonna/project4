package com.yakovchuk.sphinx.tag;

import java.util.Collection;
import java.util.HashSet;

public class CollectionUtil {

    public static boolean contains(Collection<String> requiredRoles, Collection<String> profileRoles) {
        if(requiredRoles == null || profileRoles == null){
            return false;
        }
        return new HashSet<>(profileRoles).stream().anyMatch(role -> requiredRoles.contains(role));
    }

}
