package com.jdb.dmp.service.impl;

import com.jdb.dmp.dao.EntryMapper;
import com.jdb.dmp.domain.Entry;
import com.jdb.dmp.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */


@Service("entryService")
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryMapper entryMapper;

    public List<Entry> find(String table_index, long id)
    {
        return entryMapper.find(table_index, id);
    }

    public void insert(Entry entry)
    {
        entryMapper.insert(entry);
    }

    public boolean exists(long id)
    {
        Entry entry = entryMapper.getById(id);
        if (entry != null)
            return true;
        else
            return false;
    }

    public long getMax(String table_index)
    {
        Entry entry =entryMapper.getLast(table_index);
        if (entry != null)
        {
            return entry.getId();
        }
        else
        {
            return 0;
        }
    }

    public void delete(String time)
    {
        entryMapper.delete(time);
    }

    public Entry getByUuid(String uuid) {
        return entryMapper.getByUuid(uuid);
    }

    public List<Entry> getAll() {
        return entryMapper.getAll();
    }
}
