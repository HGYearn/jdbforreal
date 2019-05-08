package com.jdb.dmp.domain;

import com.jdb.dmp.util.DateUtil;
import lombok.Data;

/**
 * Created by zhouqf on 10/09/16.
 */
public @Data class RepayFlow {
    private long id;
    private String uuid;
    private String link_uuid;
    private String laundry_uuid;
    private String from_user;
    private String to_user;
    private String total_amount;
    private String amount;
    private String principal;
    private String interest;
    private String overdue_interest;
    private String rate;
    private String overdue_day;
    private String parent_repayed_amount;
    private String parent_repay_amount;
    private String sequence;
    private String pay_status;
    private String product_code;
    private String bid_uuid;
    private String tbid_uuid;
    private String interest_day;
    private String repay_amount;
    private String link_count;
    private String start_time;
    private String end_time;
    private String create_time;
    private String update_time;
    private String parent_repay_user;
    private String delay_interest;
    private String delay_day;
    private String overdue_rate;
    private String parent_uuid;
    private String product_create_time;
    private String law_status;
    private String law_time;
    private String call_version;
    private String original_principal;
    private String type;
    private String gurantee_uuid;
    private String original_interest;
    private String source_bid_uuid;

    public boolean format()
    {
        if (this.create_time == null)
        {
            this.create_time = DateUtil.ZERO_DATE;
        }
        if (this.update_time == null)
        {
            this.update_time = DateUtil.ZERO_DATE;
        }
        if (this.start_time == null)
        {
            this.start_time = DateUtil.ZERO_DATE;
        }
        if (this.end_time == null)
        {
            this.end_time = DateUtil.ZERO_DATE;
        }
        if (this.product_create_time == null)
        {
            this.product_create_time = DateUtil.ZERO_DATE;
        }
        return true;
    }

    public boolean equals(RepayFlow compareRepayFlow)
    {
        return !(!uuid.equals(compareRepayFlow.getUuid()) ||
                !link_uuid.equals(compareRepayFlow.getLink_uuid()) ||
                !laundry_uuid.equals(compareRepayFlow.getLaundry_uuid()) ||
                !from_user.equals(compareRepayFlow.getFrom_user()) ||
                !to_user.equals(compareRepayFlow.getTo_user()) ||
                !total_amount.equals(compareRepayFlow.getTotal_amount()) ||
                !amount.equals(compareRepayFlow.getAmount()) ||
                !principal.equals(compareRepayFlow.getPrincipal()) ||
                !interest.equals(compareRepayFlow.getInterest()) ||
                !overdue_interest.equals(compareRepayFlow.getOverdue_interest()) ||
                !rate.equals(compareRepayFlow.getRate()) ||
                ((overdue_day != null && !overdue_day.equals(compareRepayFlow.getOverdue_day())) || (overdue_day == null && compareRepayFlow.getOverdue_day()!=null)) ||
                !parent_repay_amount.equals(compareRepayFlow.getParent_repay_amount()) ||
                !parent_repayed_amount.equals(compareRepayFlow.getParent_repayed_amount()) ||
                !sequence.equals(compareRepayFlow.getSequence()) ||
                !pay_status.equals(compareRepayFlow.getPay_status()) ||
                !product_code.equals(compareRepayFlow.getProduct_code()) ||
                ((bid_uuid != null && !bid_uuid.equals(compareRepayFlow.getBid_uuid())) || (bid_uuid == null && compareRepayFlow.getBid_uuid()!=null))||
                ((tbid_uuid != null && !tbid_uuid.equals(compareRepayFlow.getTbid_uuid())) || (tbid_uuid == null && compareRepayFlow.getTbid_uuid()!=null))||
                ((interest_day != null && !interest_day.equals(compareRepayFlow.getInterest_day())) || (interest_day == null && compareRepayFlow.getInterest_day()!=null))||
                !repay_amount.equals(compareRepayFlow.getRepay_amount())||
                !link_count.equals(compareRepayFlow.getLink_count()) ||
                !start_time.equals(compareRepayFlow.getStart_time())||
                !end_time.equals(compareRepayFlow.getEnd_time())||
                !create_time.equals(compareRepayFlow.getCreate_time())||
                !update_time.equals(compareRepayFlow.getUpdate_time())||
                !parent_repay_user.equals(compareRepayFlow.getParent_repay_user())||
                !delay_day.equals(compareRepayFlow.getDelay_day())||
                !delay_interest.equals(compareRepayFlow.getDelay_interest())||
                ((overdue_rate != null && !overdue_rate.equals(compareRepayFlow.getOverdue_rate())) || (overdue_rate == null && compareRepayFlow.getOverdue_rate()!=null))||
                !parent_uuid.equals(compareRepayFlow.getParent_uuid())||
                !product_create_time.equals(compareRepayFlow.getProduct_create_time())||
                !law_status.equals(compareRepayFlow.getLaw_status()) ||
                !law_time.equals(compareRepayFlow.getLaw_time())||
                !call_version.equals(compareRepayFlow.getCall_version())||
                !original_principal.equals(compareRepayFlow.getOriginal_principal())||
                !original_interest.equals(compareRepayFlow.getOriginal_interest())||
                !type.equals(compareRepayFlow.getType())||
                !gurantee_uuid.equals(compareRepayFlow.getGurantee_uuid())||
                !source_bid_uuid.equals(compareRepayFlow.getSource_bid_uuid()));
    }
}
