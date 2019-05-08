package com.jdb.dmp.service.impl;

import com.google.common.collect.Lists;
import com.jdb.dmp.dao.PhoneIncMapper;
import com.jdb.dmp.service.PhoneIncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qimwang on 11/7/16.
 */
@Service("phoneIncService")
public class PhoneIncServiceImpl implements PhoneIncService {

    @Autowired
    PhoneIncMapper phoneIncMapper;

    public List<String> getFriendPhoneInc(String myTel) {
        int tableIndex = (Integer.valueOf(myTel.substring(7)).intValue()) % 1000;
        if (tableIndex >= 0) {
            return phoneIncMapper.find(Integer.valueOf(tableIndex).toString(), myTel);
        } else {
            return Lists.newArrayList();
        }
    }
}
