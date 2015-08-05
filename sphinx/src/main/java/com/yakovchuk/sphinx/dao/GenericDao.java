package com.yakovchuk.sphinx.dao;

import java.util.Collection;

public interface GenericDao<T> {

    public T get(String id);

    public Collection<T> getAll();

    public T create(T toCreate);

    public T update(T toUpdate);

    public T delete(T toDelete);
}
