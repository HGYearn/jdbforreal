package com.jdb.dmp.service.impl;

import com.google.common.base.Joiner;
import com.jdb.dmp.dao.AutoLendCreditMapper;
import com.jdb.dmp.model.AutoLendCreditAmount;
import com.jdb.dmp.service.AutoLendCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qimwang on 11/3/16.
 */
@Service("autoLendCreditService")
public class AutoLendCreditServiceImpl implements AutoLendCreditService {
    @Autowired
    AutoLendCreditMapper autoLendCreditMapper;

    public List<AutoLendCreditAmount> getAutoLendCreditAmount(List<String> userList, Date date) {
        String uList = "'" + Joiner.on("', '").join(userList) + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return autoLendCreditMapper.getAutoLendCreditAmount(uList, format.format(date));
    }
}
