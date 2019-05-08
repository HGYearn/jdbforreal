package com.jdb.dmp.service;

import com.jdb.dmp.model.AutoLendCreditAmount;

import java.util.Date;
import java.util.List;

/**
 * Created by qimwang on 11/3/16.
 */
public interface AutoLendCreditService {
    List<AutoLendCreditAmount> getAutoLendCreditAmount(List<String> userList, Date date);
}
