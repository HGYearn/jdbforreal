package com.jdb.dmp.service;

import com.jdb.dmp.domain.AutoLendOutCredit;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */
public interface AutoLendoutCreditService {
    List<AutoLendOutCredit> find(int id, String from_time, String end_time);

    void insert(AutoLendOutCredit autoLendOutCredit);

    void delete(String updateTime);

    void update(AutoLendOutCredit autoLendOutCredit);

    boolean exists(int id);

    AutoLendOutCredit getById(int id);
}
