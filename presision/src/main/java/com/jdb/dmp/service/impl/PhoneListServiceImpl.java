package com.jdb.dmp.service.impl;

import com.google.common.collect.Lists;
import com.jdb.dmp.dao.PhoneListMapper;
import com.jdb.dmp.model.PhoneList;
import com.jdb.dmp.service.PhoneListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qimwang on 11/7/16.
 */
@Service("phoneListService")
public class PhoneListServiceImpl implements PhoneListService {
    @Autowired
    PhoneListMapper phoneListMapper;

    public List<PhoneList> getContactsByUuid(String uuid) {
        int tableIndex = ((Long.valueOf(uuid).intValue()) >> 22) % 1024;
        if (tableIndex >= 0) {
            return phoneListMapper.getContactsByUuid(Integer.valueOf(tableIndex).toString(), uuid);
        } else {
            return Lists.newArrayList();
        }
    }
}
