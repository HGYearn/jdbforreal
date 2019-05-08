package com.jdb.dmp.task.base;

import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.google.common.base.Preconditions;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;
import com.jdb.dmp.domain.*;
import com.jdb.dmp.domain.push.PushConfiguration;
import com.jdb.dmp.domain.push.PushServer;
import com.jdb.dmp.hbase.HBaseService;
import com.jdb.dmp.hbase.HistoryDataColumns;
import com.jdb.dmp.model.*;
import com.jdb.dmp.service.*;
import com.jdb.dmp.solr.SolrService;
import lombok.Getter;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 *
 * Created by qimwang on 8/22/16.
 *
 */
@Getter
public abstract class PushTask extends AbstractSimpleElasticJob {
    protected volatile int taskStatus = 0;

    @Value("${logging.path}")
    protected String pushHistoryDir;
    @Value("${hbase.table.layer}")
    protected String layerHBaseTable;

    @Value("${push.task.strategy.threads}")
    protected int strategyThreads;

    @Value("${push.task.sender.threads}")
    protected int sendThreads;

    @Value("${push.redis.complete.key.prefix}")
    protected String pushedSetKeyPrefix;

    @Value("${hbase.table.layer.column.family}")
    protected String hBaseColumnFamily;

    @Autowired
    protected IRepayService repayService;

    @Autowired
    protected EntryService entryService;

    @Autowired
    protected FriendService friendService;

    @Autowired
    protected VirtualRepayFlowService virtualRepayFlowService;

    @Autowired
    protected BorrowBuyLicaiProductService borrowBuyLicaiProductService;

    @Autowired
    protected RepayFlowService repayFlowService;

    @Autowired
    protected PushServer pushServer;

    @Autowired
    protected HBaseService hBaseService;

    @Autowired
    protected SolrService solrService;

    @Autowired
    protected AutoLendCreditService autoLendCreditService;

    @Autowired
    protected PhoneIncService phoneIncService;

    @Autowired
    protected PhoneListService phoneListService;

    @Autowired
    protected JedisPool jedisPool;


    protected Connection hBaseConnetion;
    //HBase表格
    protected Table hBaseLayerTable;
    //分层用户的队列
    protected BlockingQueue<JdbUser> layerBlockingQueue = new ArrayBlockingQueue<>(10000);
    //用户策略队列
    protected List<BlockingQueue<String>> strategyQueueList = new ArrayList<>(6);
    //任务策略列表
    protected List<Strategy> strategyList = new ArrayList(10);
    //任务消息列表
    protected List<PushConfiguration> pushConfigurationList = new ArrayList(10);

    protected Logger logger;



    public abstract void initPushConfigurationList();

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
            //确定决策
            decideStrategy(jdbUser);
            System.out.println("After doing strategy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每个任务初始化HBase连接
     *
     * @throws IOException
     */
    public void initHBaseConnection() {
        try {
            hBaseConnetion = hBaseService.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *每个线程设置一个Table
     */
    public void initHBaseTable() throws IOException {
        hBaseLayerTable = hBaseConnetion.getTable(TableName.valueOf(layerHBaseTable));
    }

    /**
     *
     */
    public void closeHBaseTable() throws IOException {
        hBaseLayerTable.close();
    }

    /**
     * 更新借贷宝用户信息
     *
     * @param jdbUser
     * @throws Exception
     */
    public void updateInfoForJdbUser(JdbUser jdbUser) throws Exception {
    }


    public void updateHisA2(JdbUser jdbUser, Result result) {
        byte[] CF = Bytes.toBytes(hBaseColumnFamily);
        int x = 1;
        if (!result.isEmpty()) {
            // 从HBase中读取历史数据
            byte[] USER_TOTAL_AMOUNT_DAYX = result.getValue(CF, Bytes.toBytes(
                    HistoryDataColumns.USER_TOTAL_AMOUNT_DAY.toString() + x));

            if (USER_TOTAL_AMOUNT_DAYX != null && USER_TOTAL_AMOUNT_DAYX.length > 0) {
                jdbUser.setHisFriendBorrowAmountA2(Integer.valueOf(new String(USER_TOTAL_AMOUNT_DAYX)));
            } else {
                jdbUser.setHisFriendBorrowAmountA2(0);
            }
        }
    }

    public void updateCurrA2(JdbUser jdbUser, Result result) {
        try {
            List<Friend> friends = friendService.getFriendsByUuid(jdbUser.getUuid());
            List<String> friendUUidList = new ArrayList(100);
            Iterator<Friend> fiter = friends.iterator();
            while (fiter.hasNext()) {
                Friend friend = fiter.next();
                friendUUidList.add(friend.getFriendId());
            }
            if (friendUUidList.size() > 0) {
                List<RepayFlowBorrowCount> rfbcList = repayFlowService.getBorrowAmount(friendUUidList, Calendar.getInstance().getTime());
                Iterator<RepayFlowBorrowCount> biter = rfbcList.iterator();
                while (biter.hasNext()) {
                    RepayFlowBorrowCount reb = biter.next();
                    if (Long.valueOf(reb.getBorrowAmount()).longValue() >= 10000) {
                        Get get = new Get(Bytes.toBytes(reb.getUuid()));
                        Result rt = hBaseLayerTable.get(get);
                        if (!rt.isEmpty()) {
                            byte[] CF = Bytes.toBytes(hBaseColumnFamily);
                            byte[] userName = rt.getValue(CF, Bytes.toBytes(
                                    HistoryDataColumns.USER_NAME.toString()));
                            jdbUser.setCurrFriendNameA2(new String(userName));
                            jdbUser.setCurrFriendUuidA2(reb.getUuid());
                            jdbUser.setCurrFriendBorrowAmountA2(Long.valueOf(reb.getBorrowAmount()).longValue());
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHisA3(JdbUser jdbUser, Result result) {

    }

    public void updateCurrA3(JdbUser jdbUser, Result result) {
        try {
            List<Friend> friends = friendService.getFriendsByUuid(jdbUser.getUuid());
            List<String> friendUUidList = new ArrayList(100);
            Iterator<Friend> fiter = friends.iterator();
            while (fiter.hasNext()) {
                Friend friend = fiter.next();
                friendUUidList.add(friend.getFriendId());
            }
            List<RepayFlowRecallMaxRate> rfrmList = repayFlowService.getRecallMaxRate(friendUUidList, Calendar.getInstance().getTime());
            Iterator<RepayFlowRecallMaxRate> biter = rfrmList.iterator();
            while (biter.hasNext()) {
                RepayFlowRecallMaxRate reb = biter.next();
                if (NumberUtils.toFloat(reb.getMaxRate()) >= 10) {
                    Get get = new Get(Bytes.toBytes(reb.getUuid()));
                    Result rt = hBaseLayerTable.get(get);
                    if (!rt.isEmpty()) {
                        byte[] CF = Bytes.toBytes(hBaseColumnFamily);
                        byte[] userName = rt.getValue(CF, Bytes.toBytes(
                                HistoryDataColumns.USER_NAME.toString()));
                        jdbUser.setCurrFriendNameA3(new String(userName));
                        jdbUser.setCurrFriendUuidA3(reb.getUuid());
                        jdbUser.setCurrFriendLendRateA3(NumberUtils.toFloat(reb.getMaxRate()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateHisA4(JdbUser jdbUser, Result result) {

    }

    public void updateCurrA4(JdbUser jdbUser, Result result) {
        try {
            List<Friend> friends = friendService.getFriendsByUuid(jdbUser.getUuid());
            List<String> friendUUidList = new ArrayList(100);
            Iterator<Friend> fiter = friends.iterator();
            while (fiter.hasNext()) {
                Friend friend = fiter.next();
                friendUUidList.add(friend.getFriendId());
            }
            List<RepayFlowLendOutMaxRate> rfrmList = repayFlowService.getLendOutMaxRate(friendUUidList, Calendar.getInstance().getTime());
            Iterator<RepayFlowLendOutMaxRate> biter = rfrmList.iterator();
            while (biter.hasNext()) {
                RepayFlowLendOutMaxRate reb = biter.next();
                if (NumberUtils.toFloat(reb.getMaxRate()) >= 9) {
                    Get get = new Get(Bytes.toBytes(reb.getUuid()));
                    Result rt = hBaseLayerTable.get(get);
                    if (!rt.isEmpty()) {
                        byte[] CF = Bytes.toBytes(hBaseColumnFamily);
                        byte[] userName = rt.getValue(CF, Bytes.toBytes(
                                HistoryDataColumns.USER_NAME.toString()));
                        jdbUser.setCurrFriendNameA4(new String(userName));
                        jdbUser.setCurrFriendUuidA4(reb.getUuid());
                        jdbUser.setCurrFriendLendRateA4(NumberUtils.toFloat(reb.getMaxRate()));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHisA5() {

    }

    public void updateCurrA5(JdbUser jdbUser, Result result) {
        try {
            List<Friend> friends = friendService.getFriendsByUuid(jdbUser.getUuid());
            List<String> friendUUidList = new ArrayList(100);
            Iterator<Friend> fiter = friends.iterator();
            while (fiter.hasNext()) {
                Friend friend = fiter.next();
                friendUUidList.add(friend.getFriendId());
            }
            List<AutoLendCreditAmount> rfrmList = autoLendCreditService.getAutoLendCreditAmount(friendUUidList, Calendar.getInstance().getTime());
            Iterator<AutoLendCreditAmount> biter = rfrmList.iterator();
            while (biter.hasNext()) {
                AutoLendCreditAmount reb = biter.next();
                if (Float.valueOf(reb.getMaxCreditAmount()).floatValue() >= 1000) {
                    Get get = new Get(Bytes.toBytes(reb.getUuid()));
                    Result rt = hBaseLayerTable.get(get);
                    if (!rt.isEmpty()) {
                        byte[] CF = Bytes.toBytes(hBaseColumnFamily);
                        byte[] userName = rt.getValue(CF, Bytes.toBytes(
                                HistoryDataColumns.USER_NAME.toString()));
                        jdbUser.setCurrFriendNameA5(new String(userName));
                        jdbUser.setCurrFriendUuidA5(reb.getUuid());
                        jdbUser.setCurrFriendCreditAmountA5(Float.valueOf(reb.getMaxCreditAmount()).floatValue());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHisB2() {

    }

    public void updateCurrB2(JdbUser jdbUser, Result result) {
//        try {
//            String entryUuid = jdbUser.getUuid();
//            Get entryGet = new Get(Bytes.toBytes(entryUuid));
//            Result ert = hBaseLayerTable.get(entryGet);
//            if (!ert.isEmpty()) {
//                byte[] CF = Bytes.toBytes(hBaseColumnFamily);
//                byte[] directLend = ert.getValue(CF, Bytes.toBytes(
//                        HistoryDataColumns.IS_DIRECT_LEND.toString()));
//                if (directLend != null && directLend.length > 0 && NumberUtils.toInt(new String(directLend)) == 1) {
//                    List<Friend> friends = friendService.getNewFriends(entryUuid);
//                    if (friends.size() > 0) {
//                        String uuid = friends.get(0).getFriendId();
//                        Get get = new Get(Bytes.toBytes(uuid));
//                        Result rt = hBaseLayerTable.get(get);
//                        if (!rt.isEmpty()) {
//                            byte[] userName = rt.getValue(CF, Bytes.toBytes(
//                                    HistoryDataColumns.USER_NAME.toString()));
//                            jdbUser.setCurrNewFriendUuidB2(uuid);
//                            jdbUser.setCurrNewFriendNameB2(new String(userName));
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void updateHisB3() {

    }

    public void updateCurrB3(JdbUser jdbUser, Result result) {
        if (PhoneRelationCache.contactMap.containsKey(jdbUser.getPhone())) {
            String newUserName = PhoneRelationCache.contactMap.get(jdbUser.getPhone());
            jdbUser.setCurrNewContactNameB3(newUserName);
        }
    }

    public void updateHisB5() {

    }

    public void updateCurrB5(JdbUser jdbUser, Result result) {

    }


    /**
     * 判读用户命中哪个策略,放入对应的队列中
     *
     * @param jdbUser
     * @throws InterruptedException
     */
    public void decideStrategy(JdbUser jdbUser) throws InterruptedException {
        int sLen = strategyList.size();
        for (int i = 0; i < sLen; i++) {
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
                logger.info(jdbUser.getUuid() + ",hit:" + i);
                this.strategyQueueList.get(i).put(jdbUser.getUuid());
                break;
            } else {

            }
        }
    }

    public void initStrategyQueueList() {
        int sLen = strategyList.size();
        for (int i = 0; i < sLen; i++) {
            strategyQueueList.add(new ArrayBlockingQueue<>(1000));
        }
    }


    public void releaseResource() {
        try {
            hBaseConnetion.close();
        } catch (Exception e) {

        }
    }

}