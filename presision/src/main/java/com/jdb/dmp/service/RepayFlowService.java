package com.jdb.dmp.service;

import com.jdb.dmp.domain.RepayFlow;
import com.jdb.dmp.model.RepayFlowBorrowCount;
import com.jdb.dmp.model.RepayFlowLendOutMaxRate;
import com.jdb.dmp.model.RepayFlowRecallMaxRate;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */
public interface RepayFlowService {
    List<RepayFlow> find(long id, String from_time, String end_time, int mod, int shard);

    void insert(RepayFlow repayFlow);

    void delete(String updateTime);

    void update(RepayFlow repayFlow);

    boolean exists(long id);

    RepayFlow getByBid(String bid);

    List<RepayFlowBorrowCount> getBorrowAmount(List<String> userList, Date date);

    List<RepayFlowLendOutMaxRate> getLendOutMaxRate(List<String> userList, Date date);

    List<RepayFlowRecallMaxRate> getRecallMaxRate(List<String> userList, Date date);
	
	RepayFlow getById(long id);
}
