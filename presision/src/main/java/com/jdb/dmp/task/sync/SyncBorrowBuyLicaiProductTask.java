package com.jdb.dmp.task.sync;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.jdb.dmp.domain.BorrowBuyLicaiProduct;
import com.jdb.dmp.service.BorrowBuyLicaiProductService;
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
public class SyncBorrowBuyLicaiProductTask extends AbstractSimpleElasticJob {

    @Autowired
    private BorrowBuyLicaiProductService borrowBuyLicaiProductService;
    Logger logger = LoggerFactory.getLogger(SyncBorrowBuyLicaiProductTask.class);
    public void process(JobExecutionMultipleShardingContext context)
    {
        logger.info("Enter SyncBorrowBuyLicaiProductTask");
        int fromId = 0;
        List<BorrowBuyLicaiProduct> list = borrowBuyLicaiProductService.find(fromId, 100);
        while (list.size() > 0)
        {
            try {
                Iterator<BorrowBuyLicaiProduct> itr = list.iterator();

                while(itr.hasNext())
                {
                    fromId++;
                    BorrowBuyLicaiProduct borrowBuyLicaiProduct = itr.next();
                    String product_uuid = borrowBuyLicaiProduct.getProduct_uuid();
                    if (borrowBuyLicaiProductService.exists(product_uuid))
                    {
                        continue;
                    }
                    borrowBuyLicaiProductService.insert(borrowBuyLicaiProduct);
                    logger.info(borrowBuyLicaiProduct.getEntry_uuid() + " " + borrowBuyLicaiProduct.getProduct_uuid() + " BorrowBuyLicaiProduct Added.");
                }
                list = borrowBuyLicaiProductService.find(fromId, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        logger.info("Exit SyncBorrowBuyLicaiProductTask");
    }
}
