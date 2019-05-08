package com.jdb.dmp.domain;

import com.jdb.dmp.util.DateUtil;
import lombok.Data;

/**
 * Created by zhouqf on 10/10/16.
 */
public @Data class VirtualRepayFlow {
    private int id;
    private String uuid;
    private String virtual_productid;
    private String to_user;
    private String amount;
    private String interest;
    private String repay_status;
    private String repay_time;
    private String create_time;
    private String update_time;

    public void format()
    {
        if (this.create_time == null)
        {
            this.create_time = DateUtil.ZERO_DATE;
        }
        if (this.update_time == null)
        {
            this.update_time = DateUtil.ZERO_DATE;
        }
    }
}
