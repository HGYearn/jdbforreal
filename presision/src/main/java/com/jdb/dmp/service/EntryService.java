package com.jdb.dmp.service;

import com.jdb.dmp.domain.Entry;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */
public interface EntryService {
    List<Entry> find(String table_index, long id);
    void insert(Entry entry);

    boolean exists(long id);

    long getMax(String table_index);

    void delete(String time);

    Entry getByUuid(String uuid);

    List<Entry> getAll();
}
