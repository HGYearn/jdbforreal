package com.jdb.dmp.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by qimwang on 8/22/16.
 */
public @Data
class JdbUser {
    private String uuid;
    private String phone;

    //历史数据注册时间
    private Date hisRegisterTime = null;
    //今日注册时间
    private Date currRegisterTime = null;

    //历史数据完成借钱买理财
    private String hisHasBuyLicaiProduct = null;
    //历史数据完成新手任务
    private String hisHasFinishNewerTask = null;

    //今日虚拟标数据
    private VirtualRepayFlow currVirtualRepayFlow = null;
    //今日购买的理财产品
    private BorrowBuyLicaiProduct currBorrowLicai = null;
    //今日借钱买理财的还款数据
    private RepayFlow currRepayFlow = null;

    //A1
    //历史逾期状态
    private boolean hisOverdue = false;
    //今日逾期状态
    private boolean currOverdue = false;

    //A2 X天内成功借入金额大于Y的好友
    private String hisFriendNameA2 = null;
    private String hisFriendUuidA2 = null;
    private int hisFriendBorrowAmountA2 = 0;

    //今日成功借入金额大于Y的好友
    private String currFriendNameA2 = null;
    private String currFriendUuidA2 = null;
    private long currFriendBorrowAmountA2 = 0;

    //A3 X天借出到期成功收回的标的利率大于Y
    private String hisFriendNameA3 = null;
    private String hisFriendUuidA3 = null;
    private float hisFriendLendRateA3 = 0;

    //今日借出到期成功收回的标的利率大于Y
    private String currFriendNameA3 = null;
    private String currFriendUuidA3 = null;
    private float currFriendLendRateA3 = 0;

    //A4 X天内出借利率大于Y
    private String hisFriendNameA4 = null;
    private String hisFriendUuidA4 = null;
    private float hisFriendLendRateA4 = 0;
    //今日出借利率大于Y
    private String currFriendNameA4 = null;
    private String currFriendUuidA4 = null;
    private float currFriendLendRateA4 = 0;

    //A5 X天内收到的授信金额大于Y
    private String hisFriendNameA5 = null;
    private String hisFriendUuidA5 = null;
    private float hisFriendCreditAmountA5 = 0;
    //今日出借利率大于Y
    private String currFriendNameA5 = null;
    private String currFriendUuidA5 = null;
    private float currFriendCreditAmountA5 = 0;


    //B2 X天内新增粉丝数大于Y
    private String hisNewFriendNameB2 = null;
    private String hisNewFriendUuidB2 = null;
    //今日新增粉丝数大于Y
    private String currNewFriendNameB2 = "TA";
    private String currNewFriendUuidB2 = null;


    //B3 X天内上传通讯录的用户中存在改用户
    private String hisNewContactNameB3 = null;
    private String hisNewContactPhoneB3 = null;
    //今日内上传通讯录的用户中存在改用户
    private String currNewContactNameB3 = null;
    private String currNewContactPhoneB3 = null;

    //B5 通讯录中X内注册的用户
    private String hisNewContactNameB5 = null;
    private String hisNewContactPhoneB5 = null;
    //今日通讯录中注册的用户
    private String currNewContactNameB5 = null;
    private String currNewContactPhoneB5 = null;



    public JdbUser(String uuid, String phone) {
        this.uuid = uuid;
        this.phone = phone;
    }

    public Date getRegisterTime() {
        if (currRegisterTime != null) {
            return currRegisterTime;
        } else {
            return hisRegisterTime;
        }
    }

//    /**
//     * 是否完成新手任务
//     *
//     * @return
//     */
//    public boolean isFinishedNewerTask() {
//        try {
//            if (c_virtualRepayFlow != null ||
//                    (h_hasFinishNewerTask != null && Integer.valueOf(Preconditions.checkNotNull(h_hasFinishNewerTask)).intValue() == 1)
//                    ) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * 是否完成借钱买理财
//     *
//     * @return
//     */
//    public boolean isFinishedBuyLicai() {
//        try {
//            if (c_repayFlow != null ||
//                    (h_hasBuyLicaiProduct != null && Integer.valueOf(Preconditions.checkNotNull(h_hasBuyLicaiProduct)).intValue() == 1)) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
