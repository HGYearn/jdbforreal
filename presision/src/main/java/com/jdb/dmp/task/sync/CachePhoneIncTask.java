package com.jdb.dmp.task.sync;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.jdb.dmp.domain.Entry;
import com.jdb.dmp.domain.Friend;
import com.jdb.dmp.model.PhoneRelationCache;
import com.jdb.dmp.service.EntryService;
import com.jdb.dmp.service.FriendService;
import com.jdb.dmp.service.PhoneIncService;
import com.jdb.util.DateUtil;
import com.jdb.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * 增量同步粉丝数据
 *
 * Created by zhouqf on 16/10/11.
 */
@Component
public class CachePhoneIncTask extends AbstractSimpleElasticJob {
    @Autowired
    private EntryService entryService;
    @Autowired
    private PhoneIncService phoneIncService;
    Logger logger = LoggerFactory.getLogger(CachePhoneIncTask.class);
    public void process(JobExecutionMultipleShardingContext context)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String version = format.format(Calendar.getInstance().getTime());
        if (PhoneRelationCache.contactMap.get("version") != version) {
            PhoneRelationCache.contactMap.clear();
            if (PhoneRelationCache.contactMap.size() == 0) {
                List<Entry> entries = entryService.getAll();
                Iterator<Entry> eiter = entries.iterator();
                while (eiter.hasNext()) {
                    Entry entry = eiter.next();
                    String entryMobile = entry.getEntryMobile();
                    String name = entry.getEntryUserName();
                    List<String> phoneIncList = phoneIncService.getFriendPhoneInc(entryMobile);
                    if (phoneIncList.size() > 1000) {
                        continue;
                    }
                    Iterator<String> phoneIter = phoneIncList.iterator();
                    while (phoneIter.hasNext()) {
                        String phone = phoneIter.next();
                        logger.info("phone: " + phone + "name: " + name);
                        PhoneRelationCache.contactMap.put(phone, name);
                    }
                }
            }
        } else {
            PhoneRelationCache.contactMap.put("version", version);
        }
    }
}
