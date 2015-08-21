package com.yakovchuk.sphinx.tag;

import java.util.Collection;
import java.util.HashSet;

public class CollectionUtil {

    public static boolean contains(Collection<String> requiredRoles, Collection<String> userRoles) {
        return new HashSet<>(userRoles).stream().anyMatch(role -> requiredRoles.contains(role));
    }

}
