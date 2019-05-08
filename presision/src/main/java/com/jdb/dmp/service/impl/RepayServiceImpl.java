package com.jdb.dmp.service.impl;

import com.google.common.base.Preconditions;
import com.jdb.dmp.dao.RepayMapper;
import com.jdb.dmp.domain.BidResult;
import com.jdb.dmp.domain.JdbUser;
import com.jdb.dmp.domain.RepayResult;
import com.jdb.dmp.domain.Result;
import com.jdb.dmp.service.IRepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qimwang on 10/11/16.
 */
@Service("repayService")
public class RepayServiceImpl implements IRepayService {

    @Autowired
    RepayMapper repayMapper;

    public boolean checkBuyLicaiFinished(JdbUser user) {
        RepayResult rr = repayMapper.getProduct("46");

        String productId = Preconditions.checkNotNull(rr).getProductUuid();

        BidResult br = repayMapper.getBidCount(productId);

        if (Preconditions.checkNotNull(br).getData().intValue() > 0)
            return true;
        else
            return false;
    }

    public Result getBuyLicaiProduct(JdbUser user) {
        try {
            RepayResult rr = repayMapper.getProduct(user.getUuid());
            return Preconditions.checkNotNull(rr);
        } catch (NullPointerException e) {
            return new RepayResult();
        }

    }
}
