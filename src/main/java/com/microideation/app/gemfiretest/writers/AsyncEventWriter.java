package com.microideation.app.gemfiretest.writers;

/**
 * Created by sandheepgr on 16/9/16.
 */
public interface AsyncEventWriter<T> {

    public T update(T obj);
    public T delete(T obj);

}
