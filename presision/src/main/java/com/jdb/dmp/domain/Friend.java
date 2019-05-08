package com.jdb.dmp.domain;

import com.jdb.dmp.util.DateUtil;
import lombok.Data;

/**
 * Created by zhouqf on 10/09/16.
 */
public @Data class Friend {
    private long id;
    private String remark;
    private String friendId;
    private String entryUuid;
    private String createTime;
    private String updateTime;
    private String valiableFlag;
    private String readFlag;
    private String source;
    private String type;
    private String allowWatchMe;
    private String heWatchMe;
    private String watchHim;
    private String heAllowWatch;
    private String remarkName;
    private String tags;
    private String followStatus;
    private String ext;
    private String followTime;
    private String fansTime;

    public boolean equals(Friend compareFriend)
    {
        return !(((remark != null && !remark.equals(compareFriend.getRemark())) || (remark == null && compareFriend.getRemark()!=null)) ||
                !friendId.equals(compareFriend.getFriendId()) ||
                !entryUuid.equals(compareFriend.getEntryUuid()) ||
                !createTime.equals(compareFriend.getCreateTime()) ||
                !updateTime.equals(compareFriend.getUpdateTime()) ||
                !valiableFlag.equals(compareFriend.getValiableFlag()) ||
                !readFlag.equals(compareFriend.getReadFlag()) ||
                !source.equals(compareFriend.getSource()) ||
                !type.equals(compareFriend.getType()) ||
                !allowWatchMe.equals(compareFriend.getAllowWatchMe()) ||
                !heWatchMe.equals(compareFriend.getHeWatchMe()) ||
                !watchHim.equals(compareFriend.getWatchHim()) ||
                !heAllowWatch.equals(compareFriend.getHeAllowWatch()) ||
                ((remarkName != null && !remarkName.equals(compareFriend.getRemarkName())) || (remarkName == null && compareFriend.getRemarkName()!=null)) ||
                !tags.equals(compareFriend.getTags()) ||
                !followStatus.equals(compareFriend.getFollowStatus()) ||
                !ext.equals(compareFriend.getExt()) ||
                !followTime.equals(compareFriend.getFollowTime()) ||
                !fansTime.equals(compareFriend.getFansTime()));
    }

    public void format()
    {
        if (this.createTime == null)
        {
            this.createTime = DateUtil.ZERO_DATE;
        }
        if (this.updateTime == null)
        {
            this.updateTime = DateUtil.ZERO_DATE;
        }
        if (this.followTime == null)
        {
            this.followTime = DateUtil.ZERO_DATE;
        }
        if (this.fansTime == null)
        {
            this.fansTime = DateUtil.ZERO_DATE;
        }
    }
}
