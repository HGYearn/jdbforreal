package com.jdb.dmp.service;

import com.jdb.dmp.domain.StrategyDomain;

import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 *
 * 通过layerType获取该层次下的所有策略
 */
public interface StrategyService {
    List<StrategyDomain> getStrategies(int layerType);

    Boolean setStrategy(byte[] strategy, int layerType);
}
