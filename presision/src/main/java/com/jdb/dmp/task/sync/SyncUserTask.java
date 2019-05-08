package com.jdb.dmp.task.sync;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.jdb.dmp.domain.Entry;
import com.jdb.dmp.service.EntryService;
import com.jdb.util.DateUtil;
import com.jdb.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 增量同步用户数据
 *
 * Created by zhouqf on 16/10/11.
 */
@Component
public class SyncUserTask extends AbstractSimpleElasticJob {

    private static int ENTRY_SHARD = 8;

    @Autowired
    private EntryService entryService;
    Logger logger = LoggerFactory.getLogger(SyncUserTask.class);
    @Override
    public void process(JobExecutionMultipleShardingContext context) {
        logger.info("Enter SyncUserTask");

        String fromTime = DateUtil.getStartTimeOfToday();
        String toTime = DateUtil.getSyncEndTime();

        entryService.delete(fromTime);
        logger.info("SyncUserTask deleted yesterday data!!");
        logger.info("SyncUserTask will start Sync data from: " + fromTime + " To " + toTime);

        for (int i = 0; i < ENTRY_SHARD;i++)
        {
            boolean stop = false;

            String table_index = NumberUtil.formatNumber(i, 4, "0");
            long fromId = entryService.getMax(table_index) + 1;
            List<Entry> list = entryService.find(table_index, fromId);
            while(list.size() > 0)
            {
                logger.info(String.valueOf(list.size()));
                Iterator<Entry> itr = list.iterator();
                while(itr.hasNext())
                {
                    Entry entry = itr.next();
                    fromId = entry.getId();
                    if (entry.getEntryRegistTime().compareTo(fromTime) < 0)
                    {
                        stop = true;
                        break;
                    }

                    if (entry.getEntryRegistTime().compareTo(toTime) > 0) {
                        continue;
                    }

                    if (entryService.exists(entry.getId()))
                    {
                        logger.info(entry.getEntryMobile() +  " " + entry.getUUID() + " Entry Exists.");
                        stop = true;
                        break;
                    }
                    entry.format();
                    entryService.insert(entry);
                    logger.info(entry.getEntryMobile() + " " + entry.getUUID() + " Entry Added.");
                }
                if (stop)
                {
                    break;
                }
                list = entryService.find(table_index, fromId);
            }
        }
        logger.info("Exit SyncUserTask");
    }
}
