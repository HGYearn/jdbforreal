package com.jdb.dmp.decisionscenes;

import com.jdb.dmp.decisionscenes.strategies.Strategy;
import com.jdb.dmp.consts.CommonConstants;
import com.jdb.dmp.consts.LayerType;
import com.jdb.dmp.domain.StrategyDomain;
import com.jdb.dmp.service.StrategyService;
import com.jdb.dmp.service.StrategyServiceImp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/11
 */
public class DecisionSelecter {

    /**
     * 通过layerType获取该层次下的策略。
     *
     * @param layerType int
     * @return List
     */
    public static List<Strategy> getStrategiesByLayerType(int layerType) throws Exception
    {
        Boolean isAllowed = false;

        for(LayerType type : LayerType.values()){
            if(type.getKey() == layerType){
                isAllowed = true;
                break;
            }
        }

        if(!isAllowed){
            throw new Exception("layerType错误");
        }else{
            StrategyService strategyService = new StrategyServiceImp();
            List<StrategyDomain> strategyDomains = strategyService.getStrategies(layerType);
            Iterator iterator = strategyDomains.iterator();

            byte[] store = new byte[CommonConstants.GENERATE_BYTES.getKey()];

            List<Strategy> strategyList = new ArrayList<>();

            ObjectInputStream inputStream;
            StrategyDomain tempStrategyDomain;
            while(iterator.hasNext()){
                tempStrategyDomain = (StrategyDomain) iterator.next();

                inputStream = new ObjectInputStream(new ByteArrayInputStream(tempStrategyDomain.getStrategy()));

                strategyList.add((Strategy) inputStream.readObject());
            }

            return strategyList;
        }
    }
}
