package com.jdb.dmp.task.sync;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.jdb.dmp.domain.Friend;
import com.jdb.dmp.service.FriendService;
import com.jdb.util.DateUtil;
import com.jdb.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 增量同步粉丝数据
 *
 * Created by zhouqf on 16/10/11.
 */
@Component
public class SyncFriendTask  extends AbstractSimpleElasticJob {

    @Value("${sync.friend.table_shard}")
    protected int friendShard;

    @Value("${sync.friend.thread_shard}")
    protected int threadShard;

    @Autowired
    private FriendService friendService;
    Logger logger = LoggerFactory.getLogger(SyncFriendTask.class);
    public void process(JobExecutionMultipleShardingContext context)
    {
        delete(friendService, logger);
        for(int i=0; i<threadShard;i++)
        {
            SyncFriendShard shard = new SyncFriendShard(friendService,logger,friendShard, threadShard, i);
            shard.start();
        }
    }

    private void delete(FriendService friendService, Logger logger)
    {
        String fromTime = DateUtil.getStartTimeOfToday();
        friendService.delete(fromTime);
        logger.info("SyncFriendTask deleted yesterday data!!");
    }
}
class SyncFriendShard extends Thread
{
    private int friend_shard;
    private int thread_shard;
    private int current_shard;

    private FriendService friendService;
    private Logger logger ;
    public SyncFriendShard(FriendService friendService, Logger logger, int friend_shard, int thread_shard, int current_shard)
    {
        this.friendService = friendService;
        this.logger = logger;
        this.friend_shard = friend_shard;
        this.thread_shard = thread_shard;
        this.current_shard = current_shard;
    }
    public void run()
    {
        logger.info("Enter SyncFriendTask shard:" + current_shard);
        long fromId = 0;
        String fromTime = DateUtil.getStartTimeOfToday();
        String toTime = DateUtil.getSyncEndTime();
        logger.info("SyncFriendTask Shard:" + current_shard + " will start Sync data from: " + fromTime + " To " + toTime);
        for (int i = 0; i < friend_shard; i++)
        {
            if (i % thread_shard != current_shard)
            {
                continue;
            }
            String table_index = NumberUtil.formatNumber(i, 4, "0");
            List<Friend> list = friendService.find(table_index,fromId, fromTime, toTime);
            while(list.size() > 0)
            {
                try {
                    Iterator<Friend> itr = list.iterator();
                    while(itr.hasNext())
                    {
                        try {
                            Friend friend = itr.next();
                            friend.format();
                            fromId = friend.getId();
                            Friend nfriend = friendService.getById(friend.getId());
                            if (nfriend != null)
                            {
                                nfriend.format();
                            }
                            if (nfriend != null && friend.equals(nfriend))
                            {
                                logger.info("Id: " + friend.getId() + " " + friend.getEntryUuid() + " " + friend.getFriendId() + " Friend Exists. Shard:" + current_shard);
                                continue;
                            }else if (nfriend != null && !friend.equals(nfriend))
                            {
                                friendService.update(friend);
                                logger.info("Id: " + friend.getId() + " " + friend.getEntryUuid() + " " + friend.getFriendId() + " Friend Updated. Shard:" + current_shard);
                            } else {
                                friendService.insert(friend);
                                logger.info("Id: " + friend.getId() + " " + friend.getEntryUuid() + " " + friend.getFriendId() + " Friend Added. Shard:" + current_shard);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    list = friendService.find(table_index,fromId, fromTime, toTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            fromId = 0;
        }
        logger.info("Exit SyncFriendTask Shard:" + current_shard);
    }
}
