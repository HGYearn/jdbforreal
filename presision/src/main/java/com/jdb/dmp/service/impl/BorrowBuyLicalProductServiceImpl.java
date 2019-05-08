package com.jdb.dmp.service.impl;

import com.jdb.dmp.dao.BorrowBuyLicaiProductMapper;
import com.jdb.dmp.domain.BorrowBuyLicaiProduct;
import com.jdb.dmp.service.BorrowBuyLicaiProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */


@Service("borrowBuyLicaiProductService")
public class BorrowBuyLicalProductServiceImpl implements BorrowBuyLicaiProductService {

    @Autowired
    private BorrowBuyLicaiProductMapper borrowBuyLicaiProductMapper;

    public List<BorrowBuyLicaiProduct> find(int from, int offset)
    {
        return borrowBuyLicaiProductMapper.find(from, offset);
    }

    public void insert(BorrowBuyLicaiProduct borrowBuyLicaiProduct)
    {
        borrowBuyLicaiProductMapper.insert(borrowBuyLicaiProduct);
    }

    public boolean exists(String product_uuid)
    {
        BorrowBuyLicaiProduct borrowBuyLicaiProduct = borrowBuyLicaiProductMapper.getById(product_uuid);
        if (borrowBuyLicaiProduct != null)
            return true;
        else
            return false;
    }

    public BorrowBuyLicaiProduct getByUuid(String uuid) {
        return borrowBuyLicaiProductMapper.getByUuid(uuid);
    }
}
