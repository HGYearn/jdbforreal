package com.jdb.dmp.task.sync;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.jdb.dmp.domain.AutoLendOutCredit;
import com.jdb.dmp.service.AutoLendoutCreditService;
import com.jdb.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * 增量同步新手任务数据
 *
 * Created by zhouqf on 16/10/11.
 */
@Component
public class SyncAutoLendOutCreditTask extends AbstractSimpleElasticJob {

    @Autowired
    private AutoLendoutCreditService autoLendoutCreditService;

    Logger logger = LoggerFactory.getLogger(SyncAutoLendOutCreditTask.class);

    public void process(JobExecutionMultipleShardingContext context)
    {
        logger.info("Enter SyncAutoLendoutCreditTask");
        int fromId = 0;
        String fromTime = DateUtil.getStartTimeOfToday();
        String toTime = DateUtil.getSyncEndTime();
        autoLendoutCreditService.delete(fromTime);
        logger.info("SyncAutoLendoutCreditTask deleted yesterday data!!");
        logger.info("SyncAutoLendoutCreditTask will start Sync data from: " + fromTime + " To " + toTime);

        List<AutoLendOutCredit> list = autoLendoutCreditService.find(fromId, fromTime,toTime);
        while (list.size() > 0)
        {
            Iterator<AutoLendOutCredit> itr = list.iterator();
            try {
                while(itr.hasNext())
                {
                    try {
                        AutoLendOutCredit autoLendOutCredit = itr.next();
                        autoLendOutCredit.format();
                        fromId = autoLendOutCredit.getId();
                        AutoLendOutCredit nAutoLendOutCredit = autoLendoutCreditService.getById(autoLendOutCredit.getId());

                        if (nAutoLendOutCredit != null && nAutoLendOutCredit.format() && !nAutoLendOutCredit.equals(autoLendOutCredit)) {
                            autoLendoutCreditService.update(autoLendOutCredit);
                            logger.info(autoLendOutCredit.getId() + " AutoLendOutCredit Update.");
                        }
                        else if (nAutoLendOutCredit != null && nAutoLendOutCredit.format() && nAutoLendOutCredit.equals(autoLendOutCredit)) {
                            logger.info(autoLendOutCredit.getId() + " AutoLendOutCredit Exists.");
                            continue;
                        }else {
                            autoLendoutCreditService.insert(autoLendOutCredit);
                            logger.info(autoLendOutCredit.getId() + " AutoLendOutCredit Added.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                list = autoLendoutCreditService.find(fromId, fromTime,toTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        logger.info("Exit SyncAutoLendoutCreditTask");
    }
}
