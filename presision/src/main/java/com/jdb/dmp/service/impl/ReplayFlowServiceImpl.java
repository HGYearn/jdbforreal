package com.jdb.dmp.service.impl;

import com.google.common.base.Joiner;
import com.jdb.dmp.dao.RepayFlowMapper;
import com.jdb.dmp.domain.RepayFlow;
import com.jdb.dmp.model.RepayFlowBorrowCount;
import com.jdb.dmp.model.RepayFlowLendOutMaxRate;
import com.jdb.dmp.model.RepayFlowRecallMaxRate;
import com.jdb.dmp.service.RepayFlowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */


@Service("repayFlowService")
public class ReplayFlowServiceImpl implements RepayFlowService {

    @Autowired
    private RepayFlowMapper repayFlowMapper;

    public List<RepayFlow> find(long id, String from_time, String end_time, int mod, int shard)
    {
        return repayFlowMapper.find(from_time, end_time, id, mod, shard);
    }

    public void insert(RepayFlow repayFlow)
    {
        repayFlowMapper.insert(repayFlow);
    }
    public void update(RepayFlow repayFlow)
    {
        repayFlowMapper.update(repayFlow);
    }
    public void delete(String updateTime)
    {
        repayFlowMapper.delete(updateTime);
    }
    public boolean exists(long id)
    {
        RepayFlow repayFlow = repayFlowMapper.getById(id);
        if (repayFlow != null)
            return true;
        else
            return false;
    }

    public RepayFlow getByBid(String bid) {
        return repayFlowMapper.getByBid(bid);
    }

    public List<RepayFlowBorrowCount> getBorrowAmount(List<String> userList, Date date) {
        String uList = "'" + Joiner.on("', '").join(userList) + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return repayFlowMapper.getBorrowAmount(uList, format.format(date));
    }

    public List<RepayFlowLendOutMaxRate> getLendOutMaxRate(List<String> userList, Date date) {
        String uList = "'" + Joiner.on("', '").join(userList) + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return repayFlowMapper.getLendOutMaxRate(uList, format.format(date));
    }

    public List<RepayFlowRecallMaxRate> getRecallMaxRate(List<String> userList, Date date) {
        String uList = "'" + Joiner.on("', '").join(userList) + "'";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return repayFlowMapper.getRecallMaxRate(uList, format.format(date));
    }
	
	public RepayFlow getById(long id)
	{
        return repayFlowMapper.getById(id);
    }
	
}
