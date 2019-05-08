package com.jdb.dmp.domain;

import com.jdb.dmp.util.DateUtil;
import lombok.Data;

/**
 * Created by zhouqf on 10/09/16.
 */
public @Data class Entry {
    private long id;
    private String UUID;
    private String entryId;
    private String entryLevel;
    private String entryMobile;
    private String entryPass;
    private String salt;
    private String entryUserName;
    private String entryIdentity;
    private String entryEmail;
    private String entryRegistTime;
    private String validateFlag;
    private String LastloginTime;
    private String entryImg;
    private String privacies;
    private String bind_card_status;
    private String id_encrypt;
    private String face_status;
    private String ac_code;

    public void format()
    {
        if (this.LastloginTime == null)
        {
            this.LastloginTime = DateUtil.ZERO_DATE;
        }
    }
}
