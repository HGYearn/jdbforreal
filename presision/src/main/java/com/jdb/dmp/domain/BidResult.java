package com.jdb.dmp.domain;

/**
 * Created by qimwang on 10/11/16.
 */
public class BidResult implements Result<Long> {
    private long bidCount;

    public Long getData() {
        return bidCount;
    }

    public void setData(Long bidCount) {
        this.bidCount = bidCount;
    }
}
