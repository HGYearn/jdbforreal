package com.jdb.dmp.service;

import com.jdb.dmp.dao.StrategyMapper;
import com.jdb.dmp.domain.StrategyDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
@Service("strategyService")
public class StrategyServiceImp implements StrategyService {

    @Autowired
    StrategyMapper strategyMapper;

    public List<StrategyDomain> getStrategies(int layerType)
    {
        return strategyMapper.getByLayerType(layerType);
    }

    public Boolean setStrategy(byte[] strategy, int layerType)
    {
        return strategyMapper.addOne(strategy, layerType, new Date());
    }
}
