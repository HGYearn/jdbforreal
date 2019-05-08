package com.jdb.dmp.domain;

import com.jdb.dmp.util.DateUtil;
import lombok.Data;

/**
 * Created by zhouqf on 10/11/16.
 */
public @Data class AutoLendOutCredit {
    private int id;
    private String uuid;
    private String entry_uuid;
    private String friend_uuid;
    private String create_time;
    private String update_time;
    private String credit_status;
    private String credit_quota;
    private String used_quota;
    private String min_rate;
    private String term_type;
    private String term_from;
    private String term_to;
    private String credit_date;
    private String credit_auto_end_date;
    private String credit_end_date;
    private String checked_time;
    private String create_contract_flag;
    private String source_from;

    public boolean format()
    {
        if (credit_date == null)
            credit_date = DateUtil.ZERO_DATE;
        if (credit_auto_end_date == null)
            credit_auto_end_date = DateUtil.ZERO_DATE;
        if (credit_end_date == null)
            credit_end_date = DateUtil.ZERO_DATE;
        if (checked_time == null)
            checked_time = DateUtil.ZERO_DATE;
        if (create_time == null)
            create_time = DateUtil.ZERO_DATE;
        if (update_time == null)
            update_time = DateUtil.ZERO_DATE;
        return true;
    }
    public boolean equals(AutoLendOutCredit compareAutoLendOutCredit)
    {
        return !(!uuid.equals(compareAutoLendOutCredit.getUuid()) ||
                !entry_uuid.equals(compareAutoLendOutCredit.getEntry_uuid()) ||
                !friend_uuid.equals(compareAutoLendOutCredit.getFriend_uuid()) ||
                !create_time.equals(compareAutoLendOutCredit.getCreate_time()) ||
                !update_time.equals(compareAutoLendOutCredit.getUpdate_time()) ||
                !credit_quota.equals(compareAutoLendOutCredit.getCredit_quota()) ||
                !used_quota.equals(compareAutoLendOutCredit.getUsed_quota()) ||
                !min_rate.equals(compareAutoLendOutCredit.getMin_rate()) ||
                !term_type.equals(compareAutoLendOutCredit.getTerm_type()) ||
                !term_from.equals(compareAutoLendOutCredit.getTerm_from()) ||
                !term_to.equals(compareAutoLendOutCredit.getTerm_to()) ||
                !credit_date.equals(compareAutoLendOutCredit.getCredit_date()) ||
                !credit_auto_end_date.equals(compareAutoLendOutCredit.getCredit_auto_end_date()) ||
                !credit_end_date.equals(compareAutoLendOutCredit.getCredit_end_date()) ||
                !checked_time.equals(compareAutoLendOutCredit.getChecked_time()) ||
                !create_contract_flag.equals(compareAutoLendOutCredit.getCreate_contract_flag()) ||
                !source_from.equals(compareAutoLendOutCredit.getSource_from()) ||
                ((credit_status != null && !credit_status.equals(compareAutoLendOutCredit.getCredit_status())) || (credit_status == null && compareAutoLendOutCredit.getCredit_status() != null)));
    }
}
