package com.jdb.dmp.domain;

/**
 * Created by qimwang on 10/11/16.
 */
public interface Result<T> {
    T getData();
    void setData(T t);
}
