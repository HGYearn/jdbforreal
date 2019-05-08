package com.jdb.dmp.service;

import com.jdb.dmp.model.PhoneList;

import java.util.List;

/**
 * Created by qimwang on 11/7/16.
 */
public interface PhoneListService {
    List<PhoneList> getContactsByUuid(String uuid);
}
