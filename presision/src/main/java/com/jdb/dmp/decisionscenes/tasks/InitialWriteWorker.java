package com.jdb.dmp.decisionscenes.tasks;

import com.jdb.dmp.decisionscenes.strategies.lastdayincreaselayer.LastDayIncreaseStrategyOne;
import com.jdb.dmp.decisionscenes.strategies.lastdayincreaselayer.LastDayIncreaseStrategyThree;
import com.jdb.dmp.decisionscenes.strategies.lastdayincreaselayer.LastDayIncreaseStrategyTwo;
import com.jdb.dmp.decisionscenes.strategies.sevendayincreaselayer.*;
import com.jdb.dmp.decisionscenes.strategies.Strategy;
import com.jdb.dmp.decisionscenes.strategies.todayincreaselayer.TodayIncreaseStrategyOne;
import com.jdb.dmp.decisionscenes.strategies.todayincreaselayer.TodayIncreaseStrategyTwo;
import com.jdb.dmp.consts.CommonConstants;
import com.jdb.dmp.consts.LayerType;
import com.jdb.dmp.service.StrategyServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
public class InitialWriteWorker {

    @Autowired
    StrategyServiceImp strategyService = new StrategyServiceImp();

    public void write() throws Exception
    {
        System.out.println("Start");

        List<Strategy> strategyList = new ArrayList<>();

        strategyList.add(new TodayIncreaseStrategyOne(24, 1));
        strategyList.add(new TodayIncreaseStrategyTwo(24, 1, 1));
        insert(strategyList, LayerType.TODAY_INCREASE_USER_ENUM.getKey());
        strategyList.clear();

        strategyList.add(new SevenIncreaseStrategyOne(2));
        strategyList.add(new SevenIncreaseStrategyTwo(3, 1));
        strategyList.add(new SevenIncreaseStrategyThree(3, 1, 1));
        strategyList.add(new SevenIncreaseStrategyFour(4));
        strategyList.add(new SevenIncreaseStrategyFive(6));
        insert(strategyList, LayerType.SEVEN_DAY_INCREASE_USER_ENUM.getKey());
        strategyList.clear();

        strategyList.add(new LastDayIncreaseStrategyOne(10));
        strategyList.add(new LastDayIncreaseStrategyTwo(20, 1));
        strategyList.add(new LastDayIncreaseStrategyThree(20, 1, 1));
        insert(strategyList, LayerType.LAST_DAY_INCREASE_USER_ENUM.getKey());
        strategyList.clear();

        System.out.println("Finish");
    }

    private void insert(List<Strategy> strategyList, int layerType) throws Exception
    {
        Iterator<Strategy> iterator = strategyList.iterator();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream(CommonConstants.GENERATE_BYTES.getKey()));

            while(iterator.hasNext()){
                objectOutputStream.writeObject(iterator.next());

                insert(objectOutputStream.toString().getBytes(), layerType);

                objectOutputStream.reset();
            }

            objectOutputStream.close();
    }

    /**
     * 将字节输出流写入mysql
     * @param bytes byte[] Strategy
     * @param layerType int 分层
     */
    private void insert(byte[] bytes, int layerType) throws Exception
    {
        System.out.println(bytes.toString());

//        Boolean result = strategyService.setStrategy(bytes, layerType);
//        if(!result){
//            throw new Exception("initial set error");
//        }
    }
}
