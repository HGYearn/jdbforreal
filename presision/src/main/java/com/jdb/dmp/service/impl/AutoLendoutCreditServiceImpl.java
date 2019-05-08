package com.jdb.dmp.service.impl;

import com.jdb.dmp.dao.AutoLendOutCreditMapper;
import com.jdb.dmp.domain.AutoLendOutCredit;
import com.jdb.dmp.service.AutoLendoutCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */


@Service("autoLendoutCreditService")
public class AutoLendoutCreditServiceImpl implements AutoLendoutCreditService {

    @Autowired
    private AutoLendOutCreditMapper autoLendOutCreditMapper;

    public List<AutoLendOutCredit> find(int id, String from_time, String end_time)
    {
        return autoLendOutCreditMapper.find(from_time, end_time, id);
    }

    public void insert(AutoLendOutCredit autoLendOutCredit)
    {
        autoLendOutCreditMapper.insert(autoLendOutCredit);
    }
    public void update(AutoLendOutCredit autoLendOutCredit)
    {
        autoLendOutCreditMapper.update(autoLendOutCredit);
    }
    public void delete(String credit_date)
    {
        autoLendOutCreditMapper.delete(credit_date);
    }
    public boolean exists(int id)
    {
        AutoLendOutCredit autoLendOutCredit = autoLendOutCreditMapper.getById(id);
        if (autoLendOutCredit != null)
            return true;
        else
            return false;
    }

    public AutoLendOutCredit getById(int id)
    {
        return autoLendOutCreditMapper.getById(id);
    }
}
