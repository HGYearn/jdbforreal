package com.jdb.dmp.task.sync;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.jdb.dmp.domain.RepayFlow;
import com.jdb.dmp.service.RepayFlowService;
import com.jdb.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 增量同步还款数据 - 包括普通还款和借钱买理财还款数据
 *
 *
 * Created by zhouqf on 16/10/11.
 */
@Component
public class SyncRepayFlowTask  extends AbstractSimpleElasticJob {

    @Value("${sync.repay_flow.thread_shard}")
    protected int threadShard;

    @Autowired
    private RepayFlowService repayFlowService;
    Logger logger = LoggerFactory.getLogger(SyncRepayFlowTask.class);
    public void process(JobExecutionMultipleShardingContext context)
    {
        delete(repayFlowService,logger);
        for(int i=0; i<threadShard;i++)
        {
            SyncRepayFlowShard shard = new SyncRepayFlowShard(repayFlowService, logger, threadShard, i);
            shard.start();
        }
    }

    private void delete(RepayFlowService repayFlowService, Logger logger )
    {
        String fromTime = DateUtil.getStartTimeOfToday();
        repayFlowService.delete(fromTime);
        logger.info("SyncRepayFlowTask deleted yesterday data!!");
    }
}
class SyncRepayFlowShard extends Thread
{
    private RepayFlowService repayFlowService;
    private Logger logger;
    private int mod;
    private int shard;
    public SyncRepayFlowShard(RepayFlowService repayFlowService, Logger logger, int mod, int shard)
    {
        this.repayFlowService = repayFlowService;
        this.logger = logger;
        this.mod = mod;
        this.shard = shard;
    }
    public void run()
    {
        try {
            logger.info("Enter SyncRepayFlowTask Shard:" + shard);
            long fromId = 0;
            String fromTime = DateUtil.getStartTimeOfToday();
            String toTime = DateUtil.getSyncEndTime();
            logger.info("SyncRepayFlowTask Shard:"  + shard + " will start Sync data from: " + fromTime + " To " + toTime);
            List<RepayFlow> list = repayFlowService.find(fromId, fromTime,toTime, mod, shard);
            while (list.size() > 0)
            {
                Iterator<RepayFlow> itr = list.iterator();

                while(itr.hasNext())
                {
                    RepayFlow repayFlow = itr.next();
                    repayFlow.format();

                    fromId = repayFlow.getId();
                    if (fromId % mod != shard)
                    {
                        continue;
                    }

                    RepayFlow nRepayFlow = repayFlowService.getById(repayFlow.getId());

                    if (nRepayFlow != null && nRepayFlow.format() && !nRepayFlow.equals(repayFlow)) {
                        repayFlowService.update(repayFlow);
                        logger.info("Id: " + repayFlow.getId() + " RepayFlow Update. Shard:" + shard);
                    }
                    else if (nRepayFlow != null && nRepayFlow.format() && nRepayFlow.equals(repayFlow)) {
                        logger.info("Id: " + repayFlow.getId() + " RepayFlow Exists. Shard:" + shard);
                        continue;
                    }else {
                        repayFlowService.insert(repayFlow);
                        logger.info("Id: " + repayFlow.getId() + " RepayFlow Added. Shard:" + shard);
                    }
                }
                list = repayFlowService.find(fromId, fromTime,toTime, mod, shard);
            }
            logger.info("Exit SyncRepayFlowTask Shard:" + shard);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
