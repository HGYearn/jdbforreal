package com.jdb.dmp.task.push;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.google.common.base.Preconditions;
import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.decisionscenes.conditions.other.BorrowToBuyFinProdCondition;
import com.jdb.dmp.decisionscenes.conditions.other.NewbieMissionCondition;
import com.jdb.dmp.decisionscenes.conditions.registerdate.RegisterDateByDayCondition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;
import com.jdb.dmp.domain.*;
import com.jdb.dmp.domain.push.PushConfiguration;
import com.jdb.dmp.hbase.HistoryDataColumns;
import com.jdb.dmp.task.base.FileReaderThread;
import com.jdb.dmp.task.base.PushSenderThread;
import com.jdb.dmp.task.base.PushTask;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by qimwang on 11/7/16.
 */
@Component
public class SevenNewUserPushTask extends PushTask {
    @Value("${layer.file.dir.sevendays}")
    String layerFileDir;
    List<String> pushMessageList = new ArrayList<>(6);
    String messageForS1 = "你身边的有钱人都在借贷宝上借钱，这是为什么呢？";
    String messageForS2 = "借贷宝出本金，你拿收益；现在就领取体验金白赚钱！";
    String messageForS3 = "不出钱赚钱真的不是梦";
    String messageForS4 = "有人看了这本指南，走上了月入百万的人生巅峰，你要试试吗？";
    String messageForS5 = "一分钟读懂借贷宝的催收体系";
    String pushTitle = "借贷宝";

    @Override
    public void process(JobExecutionMultipleShardingContext context) {
        try {
            logger = LoggerFactory.getLogger(SevenNewUserPushTask.class);
            System.out.println("========= start ============");
            //实例化策略
            if (strategyList.size() == 0)
                instanceStrategyList();

            //初始化HBaseConnection
            if (hBaseConnetion == null)
                initHBaseConnection();

            //初始化策略队列
            if (strategyQueueList.size() == 0)
                initStrategyQueueList();

            //初始化push消息列表
            if (pushConfigurationList.size() == 0)
                initPushConfigurationList();

            SimpleDateFormat sdfd = new SimpleDateFormat("yyyMMdd");
            Calendar cal = Calendar.getInstance();
            String fileName = sdfd.format(cal.getTime());

            //读取用户分成的数据
            FileReaderThread readerThread = new FileReaderThread(layerFileDir, fileName);
            readerThread.setHistoryPushDir(pushHistoryDir);
            readerThread.setTask(this);
            readerThread.start();

            Thread.sleep(1000);
            // 启动线程读取用户列表
            int handlerThreads = getStrategyThreads();
            while (handlerThreads > 0) {
                com.jdb.dmp.task.base.StrategyHandlerThread strategyHandlerThread = new com.jdb.dmp.task.base.StrategyHandlerThread();
                strategyHandlerThread.setTask(this);
                strategyHandlerThread.start();
                handlerThreads--;
            }

            //启动发送push线程
            int sendThreads = getSendThreads();
            while (sendThreads > 0) {
                PushSenderThread pushSender = new PushSenderThread(SevenNewUserPushTask.class);
                pushSender.setTask(this);
                pushSender.setPushServer(pushServer);
                pushSender.start();
                sendThreads--;
            }

            System.out.println("=========end============");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分发策略
     *
     */
    public void dispatchStrategy() {
        try {
            JdbUser jdbUser = layerBlockingQueue.poll(200, TimeUnit.SECONDS);
            jdbUser = Preconditions.checkNotNull(jdbUser);
            System.out.println("hbase history table: " + layerHBaseTable);
            System.out.println("jdbUser " + jdbUser.getUuid());
            //更新用户信息
            updateInfoForJdbUser(jdbUser);
            //确定策略
            decideStrategy(jdbUser);
            System.out.println("After doing strategy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInfoForJdbUser(JdbUser jdbUser) throws Exception {
        Get get = new Get(Bytes.toBytes(jdbUser.getUuid()));
        Result rt = hBaseLayerTable.get(get);
        System.out.println("data from hbase " + rt.toString());
        rt = Preconditions.checkNotNull(rt);
        byte[] CF = Bytes.toBytes(hBaseColumnFamily);
        if (!rt.isEmpty()) {
            // 从HBase中读取历史数据
            byte[] registerTimeBytes = rt.getValue(CF, Bytes.toBytes(
                    HistoryDataColumns.USER_REGISTER_TIME.toString()));

            byte[] hasBuyLicaiProductBytes = rt.getValue(CF, Bytes.toBytes(
                    HistoryDataColumns.IS_FINISH_BORROW_BUY_FINANCE.toString()));

            byte[] hasFinishNewerTaskBytes = rt.getValue(CF, Bytes.toBytes(
                    HistoryDataColumns.IS_FINISH_NEWERTASK.toString()));

            System.out.println(rt.toString());
            System.out.println(new String(registerTimeBytes));
            String hasBuyLicaiProduct = null;
            String hasFinishNewerTask = null;
            VirtualRepayFlow virtualRepayFlow = null;
            BorrowBuyLicaiProduct bbLicai = null;
            RepayFlow repayFlow = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (registerTimeBytes != null && registerTimeBytes.length > 0) {
                jdbUser.setHisRegisterTime(format.parse(new String(registerTimeBytes).substring(0, 19)));
            }

            // 从mysql中读取当天更新的数据

            Entry entry = entryService.getByUuid(jdbUser.getUuid());
            if (entry != null) {
                jdbUser.setCurrRegisterTime(format.parse(entry.getEntryRegistTime()));
            }

            if (hasBuyLicaiProductBytes != null && hasBuyLicaiProductBytes.length > 0) {
                jdbUser.setHisHasBuyLicaiProduct(new String(hasBuyLicaiProductBytes));
            }

            if (hasFinishNewerTaskBytes != null && hasFinishNewerTaskBytes.length > 0) {
                jdbUser.setHisHasFinishNewerTask(new String(hasFinishNewerTaskBytes));
            }
            virtualRepayFlow = virtualRepayFlowService.getByUuid(jdbUser.getUuid());
            bbLicai = borrowBuyLicaiProductService.getByUuid(jdbUser.getUuid());

            if (bbLicai != null) {
                repayFlow = repayFlowService.getByBid(bbLicai.getProduct_uuid());
                jdbUser.setCurrRepayFlow(repayFlow);
            }
        }
    }

    /**
     * 初始化Push 消息
     *
     */
    public void initPushConfigurationList() {
        pushMessageList.add(messageForS1);
        pushMessageList.add(messageForS2);
        pushMessageList.add(messageForS3);
        pushMessageList.add(messageForS4);
        pushMessageList.add(messageForS5);

        //策略1
        PushConfiguration.Builder builder = new PushConfiguration.Builder();
        builder.newInstance().setContent(messageForS1).setDescription(messageForS1)
                .setUrl("http://invest.jiedaibao.com.cn/1261.html").setTitle(pushTitle);
        pushConfigurationList.add(builder.build());

        //策略2
        builder.newInstance().setContent(messageForS2).setDescription(messageForS2)
                .setTitle(pushTitle).setSubtype(104);
        pushConfigurationList.add(builder.build());

        //策略3
        builder.newInstance().setContent(messageForS3).setDescription(messageForS3)
                .setTitle(pushTitle).setSubtype(105);
        pushConfigurationList.add(builder.build());

        //策略4
        builder.newInstance().setContent(messageForS4).setDescription(messageForS4)
                .setUrl("http://invest.jiedaibao.com.cn/1264.html").setTitle(pushTitle);
        pushConfigurationList.add(builder.build());

        //策略5
        builder.newInstance().setContent(messageForS5).setDescription(messageForS5)
                .setUrl("http://invest.jiedaibao.com.cn/1205.html").setTitle(pushTitle);
        pushConfigurationList.add(builder.build());
    }
    /**
     * 初始化策略
     *
     */
    public void instanceStrategyList() {
        BorrowToBuyFinProdCondition condition1 = new BorrowToBuyFinProdCondition(0, ConditionJudgeType.MISS.getValue());
        NewbieMissionCondition condition2 = new NewbieMissionCondition(0, ConditionJudgeType.MISS.getValue());
        NewbieMissionCondition condition3 = new NewbieMissionCondition(0, ConditionJudgeType.MEET.getValue());

        RegisterDateByDayCondition day2Condition = new RegisterDateByDayCondition(2, ConditionJudgeType.MEET.getValue());
        RegisterDateByDayCondition day3Condition = new RegisterDateByDayCondition(3, ConditionJudgeType.MEET.getValue());
        RegisterDateByDayCondition day4Condition = new RegisterDateByDayCondition(4, ConditionJudgeType.MEET.getValue());
        RegisterDateByDayCondition day6Condition = new RegisterDateByDayCondition(6, ConditionJudgeType.MEET.getValue());


        Strategy strategy1 = new Strategy() {
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(day2Condition);
                return list;
            }
        };

        Strategy strategy2 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(day3Condition);
                list.add(condition2);
                return list;
            }
        };

        Strategy strategy3 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(3);
                list.add(day3Condition);
                list.add(condition3);
                list.add(condition1);
                return list;
            }
        };

        Strategy strategy4 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(1);
                list.add(day4Condition);
                return list;
            }
        };

        Strategy strategy5 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(1);
                list.add(day6Condition);
                return list;
            }
        };

        strategyList.add(strategy1);
        strategyList.add(strategy2);
        strategyList.add(strategy3);
        strategyList.add(strategy4);
        strategyList.add(strategy5);
    }
}
