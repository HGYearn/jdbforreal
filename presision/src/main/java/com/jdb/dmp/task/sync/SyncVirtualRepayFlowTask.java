package com.jdb.dmp.task.sync;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.jdb.dmp.domain.VirtualRepayFlow;
import com.jdb.dmp.service.VirtualRepayFlowService;
import com.jdb.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 增量同步新手任务数据
 *
 * Created by zhouqf on 16/10/11.
 */
@Component
public class SyncVirtualRepayFlowTask  extends AbstractSimpleElasticJob {

    @Autowired
    private VirtualRepayFlowService virtualRepayFlowService;

    Logger logger = LoggerFactory.getLogger(SyncVirtualRepayFlowTask.class);

    public void process(JobExecutionMultipleShardingContext context)
    {
        logger.info("Enter SyncVirtualRepayFlowTask");
        int fromId = 0;
        String fromTime = DateUtil.getStartTimeOfToday();
        String toTime = DateUtil.getSyncEndTime();
        virtualRepayFlowService.delete(fromTime);
        logger.info("SyncVirtualRepayFlowTask deleted yesterday data!!");
        logger.info("SyncVirtualRepayFlowTask will start Sync data from: " + fromTime + " To " + toTime);

        List<VirtualRepayFlow> list = virtualRepayFlowService.find(fromId, fromTime,toTime);
        while (list.size() > 0)
        {
            Iterator<VirtualRepayFlow> itr = list.iterator();

            while(itr.hasNext())
            {

                VirtualRepayFlow virtualRepayFlow = itr.next();
                fromId = virtualRepayFlow.getId();
                if (virtualRepayFlowService.exists(virtualRepayFlow.getId()))
                {
                    logger.info(virtualRepayFlow.getId() + " VirtualRepayFlow Exists.");
                    continue;
                }
                virtualRepayFlow.format();
                virtualRepayFlowService.insert(virtualRepayFlow);
                logger.info(virtualRepayFlow.getId() + " VirtualRepayFlow Added.");
            }
            list = virtualRepayFlowService.find(fromId, fromTime,toTime);
        }
        logger.info("Exit SyncVirtualRepayFlowTask");
    }
}
