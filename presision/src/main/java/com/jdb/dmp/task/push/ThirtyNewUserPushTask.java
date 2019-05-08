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
public class ThirtyNewUserPushTask extends PushTask {
    @Value("${layer.file.dir.thirtydays}")
    String layerFileDir;
    List<String> pushMessageList = new ArrayList<>(6);
    String messageForS1 = "告诉你一个秘密，借贷宝为何是行业NO.1!";
    String messageForS2 = "借贷宝出本金，你拿收益！现在领取体验金马上白赚钱!";
    String messageForS3 = "不出钱赚钱真的不是梦";
    String pushTitle = "借贷宝";

    @Override
    public void process(JobExecutionMultipleShardingContext context) {
        try {
            logger = LoggerFactory.getLogger(ThirtyNewUserPushTask.class);
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
                PushSenderThread pushSender = new PushSenderThread(ThirtyNewUserPushTask.class);
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

        //策略1
        PushConfiguration.Builder builder = new PushConfiguration.Builder();
        builder.newInstance().setContent(messageForS1).setDescription(messageForS1)
                .setUrl("http://invest.jiedaibao.com.cn/1266.html").setTitle(pushTitle);
        pushConfigurationList.add(builder.build());

        //策略2
        builder.newInstance().setContent(messageForS2).setDescription(messageForS2)
                .setSubtype(104).setTitle(pushTitle);
        pushConfigurationList.add(builder.build());

        //策略3
        builder.newInstance().setContent(messageForS3).setDescription(messageForS3)
                .setSubtype(105).setTitle(pushTitle);
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

        RegisterDateByDayCondition day10Condition = new RegisterDateByDayCondition(10, ConditionJudgeType.MEET.getValue());
        RegisterDateByDayCondition day20Condition = new RegisterDateByDayCondition(20, ConditionJudgeType.MEET.getValue());


        Strategy strategy1 = new Strategy() {
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(day10Condition);
                return list;
            }
        };

        Strategy strategy2 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(day20Condition);
                list.add(condition2);
                return list;
            }
        };

        Strategy strategy3 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(3);
                list.add(day20Condition);
                list.add(condition3);
                list.add(condition1);
                return list;
            }
        };
        strategyList.add(strategy1);
        strategyList.add(strategy2);
        strategyList.add(strategy3);
    }
}
