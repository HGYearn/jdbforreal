package com.jdb.dmp.service;

import com.jdb.dmp.domain.VirtualRepayFlow;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */
public interface VirtualRepayFlowService {
    List<VirtualRepayFlow> find(long id, String from_time, String end_time);

    void insert(VirtualRepayFlow virtualRepayFlow);

    void delete(String updateTime);

    boolean exists(long id);

    VirtualRepayFlow getByUuid(String uuid);
}
