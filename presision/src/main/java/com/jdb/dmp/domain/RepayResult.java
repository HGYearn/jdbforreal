package com.jdb.dmp.domain;

import lombok.Data;

/**
 * Created by qimwang on 10/11/16.
 */
public @Data
class RepayResult implements Result<String> {

    private String productUuid;
    private String entryUuid;

    public String getData() {
        return productUuid;
    }

    public void setData(String productUuid) {
        this.productUuid = productUuid;
    }
}
