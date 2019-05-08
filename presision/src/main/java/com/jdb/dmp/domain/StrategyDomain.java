package com.jdb.dmp.domain;

import lombok.Data;

import java.util.Date;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
@Data
public class StrategyDomain {
    private byte[] strategy;
    private int layerType;
    private Date timeCreated;

    public StrategyDomain(byte[] strategy, int layerType, Date timeCreated)
    {
        this.strategy = strategy;
        this.layerType = layerType;
        this.timeCreated = timeCreated;
    }

    public byte[] getStrategy()
    {
        return strategy;
    }

    public int getLayerType()
    {
        return layerType;
    }

    public Date getTimeCreated()
    {
        return timeCreated;
    }
}
