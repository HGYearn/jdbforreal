package com.jdb.dmp.task.push;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.google.common.base.Preconditions;
import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.*;
import com.jdb.dmp.decisionscenes.conditions.overdue.OverdueCondition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;
import com.jdb.dmp.domain.JdbUser;
import com.jdb.dmp.domain.push.PushConfiguration;
import com.jdb.dmp.task.base.FileReaderThread;
import com.jdb.dmp.util.PushUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static net.logstash.logback.marker.Markers.append;

/**
 * Created by qimwang on 11/7/16.
 */
@Component
public class NearLossUserPushTask extends com.jdb.dmp.task.base.PushTask {
    Logger logger = LoggerFactory.getLogger(NearLossUserPushTask.class);
    @Value("${layer.file.dir.nearloss}")
    String layerFileDir;
    List<String> pushMessageList = new ArrayList<>(6);
    String messageForS1 = "您的好友%s借到了%s元; 投资熟人赚收益, 理财新体验！";
    String messageForS2 = "您的好友%s借出款项到期收回, 收益率高达年化%s, 直投熟人更高明！";
    String messageForS3 = "您的好友%s今天投资了一笔熟人借款, 年化收益高达%s, 直投熟人更高明";
    String messageForS4 = "您新增加了一位粉丝%s, 好友多多玩转借贷宝";
    String messageForS5 = "您的好友%s今天被朋友授信了%s元，TA只要发布合适利率马上就能借到钱！要不你也试试？";
    String messageForS6 = "%s新加入了借贷宝，来瞅瞅新朋友吧";
    String pushTitle = "借贷宝";

    @Override
    public void process(JobExecutionMultipleShardingContext context) {
        try {
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
            //确定策略并发送push
            decideAndPush(jdbUser);
            System.out.println("After doing strategy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在判断命中策略之后直接发送push
     *
     * @Params jdbUser
     * @throws Exception
     */
    public void decideAndPush(JdbUser jdbUser) throws Exception {
//        Get get = new Get(Bytes.toBytes(jdbUser.getUuid()));
//        Result rt = hBaseLayerTable.get(get);
        int sLen = strategyList.size();
        int i = 0;
        for (; i < sLen; i++) {
            //不同策略更新不同的数据
            switch (i) {
                case 0:
                    updateCurrA2(jdbUser, null);
                    break;
                case 1:
                    updateCurrA3(jdbUser, null);
                    break;
                case 2:
                    updateCurrA4(jdbUser, null);
                    break;
                case 3:
                    updateCurrB2(jdbUser, null);
                    break;
                case 4:
                    updateCurrA5(jdbUser, null);
                    break;
                case 5:
                    updateCurrB3(jdbUser, null);
                    break;
            }

            Strategy s1 = strategyList.get(i);
            boolean sResult = true;
            List<Condition> conditions = s1.getConditions();
            Iterator<Condition> citer = conditions.iterator();
            while (citer.hasNext()) {
                Condition condition = citer.next();
                if (!condition.isSatisfied(jdbUser)) {
                    sResult = false;
                    break;
                }
            }
            if (sResult) {
                //命中策略之后直接发送push
                PushConfiguration pushConfiguration = this.pushConfigurationList.get(i);
                Marker marker;
                switch (i) {
                    case 0:
                        pushConfiguration.setContent(String.format(pushMessageList.get(i), jdbUser.getCurrFriendNameA2(), jdbUser.getCurrFriendBorrowAmountA2()));
                        marker = append("uuid", jdbUser.getUuid()).and(append("strategy", i)).and(append("friend", jdbUser.getCurrFriendUuidA2()));
                        logger.info(marker, "hit-strategy");
                        break;
                    case 1:
                        pushConfiguration.setContent(String.format(pushMessageList.get(i), jdbUser.getCurrFriendNameA3(), jdbUser.getCurrFriendLendRateA3()));
                        marker = append("uuid", jdbUser.getUuid()).and(append("strategy", i)).and(append("friend", jdbUser.getCurrFriendUuidA3()));
                        logger.info(marker, "hit-strategy");
                        break;
                    case 2:
                        pushConfiguration.setContent(String.format(pushMessageList.get(i), jdbUser.getCurrFriendNameA4(), jdbUser.getCurrFriendLendRateA4()));
                        marker = append("uuid", jdbUser.getUuid()).and(append("strategy", i)).and(append("friend", jdbUser.getCurrFriendUuidA4()));
                        logger.info(marker, "hit-strategy");
                        break;
                    case 3:
                        pushConfiguration.setContent(String.format(pushMessageList.get(i), jdbUser.getCurrNewFriendNameB2()));
                        marker = append("uuid", jdbUser.getUuid()).and(append("strategy", i)).and(append("friend", jdbUser.getCurrNewFriendUuidB2()));
                        logger.info(marker, "hit-strategy");
                        break;
                    case 4:
                        pushConfiguration.setContent(String.format(pushMessageList.get(i), jdbUser.getCurrFriendNameA5(), jdbUser.getCurrFriendCreditAmountA5()));
                        marker = append("uuid", jdbUser.getUuid()).and(append("strategy", i)).and(append("friend", jdbUser.getCurrFriendUuidA5()));
                        logger.info(marker, "hit-strategy");
                        break;
                    case 5:
                        pushConfiguration.setContent(String.format(pushMessageList.get(i), jdbUser.getCurrNewContactNameB3()));
                        marker = append("uuid", jdbUser.getUuid()).and(append("strategy", i)).and(append("friend", jdbUser.getCurrNewContactPhoneB5()));
                        logger.info(marker, "hit-strategy");
                        break;
                }
                PushUtil.sendPush(pushConfiguration.toJsonString(), jdbUser.getUuid(), pushServer.getPushUrl(), logger);
                break;
            } else {

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
        pushMessageList.add(messageForS6);
        //策略1
        PushConfiguration.Builder builder = new PushConfiguration.Builder();
        builder.newInstance().setContent(messageForS1).setDescription(messageForS1)
                .setGroup(this.getClass().getName() + "_1")
                .setTitle(pushTitle).setSubtype(104);
        pushConfigurationList.add(builder.build());

        //策略2
        builder.newInstance().setContent(messageForS2).setDescription(messageForS2)
                .setGroup(this.getClass().getName() + "_2")
                .setTitle(pushTitle).setSubtype(104);
        pushConfigurationList.add(builder.build());
        //策略3
        builder.newInstance().setContent(messageForS3).setDescription(messageForS3)
                .setGroup(this.getClass().getName() + "_3")
                .setTitle(pushTitle).setSubtype(104);
        pushConfigurationList.add(builder.build());
        //策略4
        builder.newInstance().setContent(messageForS4).setDescription(messageForS4)
                .setGroup(this.getClass().getName() + "_4")
                .setTitle(pushTitle).setSubtype(104);
        pushConfigurationList.add(builder.build());
        //策略5
        builder.newInstance().setContent(messageForS5).setDescription(messageForS5)
                .setGroup(this.getClass().getName() + "_5")
                .setTitle(pushTitle).setSubtype(104);
        pushConfigurationList.add(builder.build());
        //策略6
        builder.newInstance().setContent(messageForS6).setDescription(messageForS6)
                .setGroup(this.getClass().getName() + "_6")
                .setTitle(pushTitle).setSubtype(104);
        pushConfigurationList.add(builder.build());
    }


    /**
     * 初始化策略
     *
     */
    public void instanceStrategyList() {
        OverdueCondition overdueCondition = new OverdueCondition(ConditionJudgeType.MISS.getValue());

        FriendBorrowAmountA2 friendBorrowAmountA2 = new FriendBorrowAmountA2(0, 10000, ConditionJudgeType.MEET.getValue());

        FriendCreditAmountA5 friendCreditAmountA5 = new FriendCreditAmountA5(0, 1000, ConditionJudgeType.MEET.getValue());

        FriendLendRateA4 friendLendRateA4 = new FriendLendRateA4(0, 10, ConditionJudgeType.MEET.getValue());

        FriendLendRecallA3 friendLendRecallA3 = new FriendLendRecallA3(0, 10, ConditionJudgeType.MEET.getValue());

        NewFriendB2 newFriendB2 = new NewFriendB2();

        NewFriendFromContactB3 newFriendFromContactB3 = new NewFriendFromContactB3();

        NewUserFromContactB5 newUserFromContactB5 = new NewUserFromContactB5();

        Strategy strategy1 = new Strategy() {
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(friendBorrowAmountA2);
                return list;
            }
        };

        Strategy strategy2 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(friendLendRecallA3);
                return list;
            }
        };

        Strategy strategy3 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(friendLendRateA4);
                return list;
            }
        };

        Strategy strategy4 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(newFriendB2);
                return list;
            }
        };

        Strategy strategy5 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(friendCreditAmountA5);
                return list;
            }
        };

        Strategy strategy6 = new Strategy() {
            @Override
            public List<Condition> getConditions() {
                ArrayList<Condition> list = new ArrayList(2);
                list.add(newFriendFromContactB3);
                return list;
            }
        };

        strategyList.add(strategy1);
        strategyList.add(strategy2);
        strategyList.add(strategy3);
        strategyList.add(strategy4);
        strategyList.add(strategy5);
        strategyList.add(strategy6);
    }
}
