package com.jdb.dmp.service.impl;

import com.jdb.dmp.dao.VirtualRepayFlowMapper;
import com.jdb.dmp.domain.VirtualRepayFlow;
import com.jdb.dmp.service.VirtualRepayFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */


@Service("virtualRepayFlowService")
public class VirtualReplayFlowServiceImpl implements VirtualRepayFlowService {

    @Autowired
    private VirtualRepayFlowMapper virtualRepayFlowMapper;

    public List<VirtualRepayFlow> find(long id, String from_time, String end_time)
    {
        return virtualRepayFlowMapper.find(from_time, end_time, id);
    }

    public void insert(VirtualRepayFlow virtualRepayFlow)
    {
        virtualRepayFlowMapper.insert(virtualRepayFlow);
    }

    public void delete(String updateTime)
    {
        virtualRepayFlowMapper.delete(updateTime);
    }

    public boolean exists(long id)
    {
        VirtualRepayFlow virtualRepayFlow = virtualRepayFlowMapper.getById(id);
        if (virtualRepayFlow != null)
            return true;
        else
            return false;
    }

    public VirtualRepayFlow getByUuid(String uuid) {
        return virtualRepayFlowMapper.getByUuid(uuid);
    }
}
