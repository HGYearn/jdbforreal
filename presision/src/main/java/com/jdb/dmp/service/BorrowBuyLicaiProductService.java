package com.jdb.dmp.service;

import com.jdb.dmp.domain.BorrowBuyLicaiProduct;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */
public interface BorrowBuyLicaiProductService {

    List<BorrowBuyLicaiProduct> find(int from, int offset);

    void insert(BorrowBuyLicaiProduct borrowBuyLicaiProduct);

    boolean exists(String product_uuid);

    BorrowBuyLicaiProduct getByUuid(String uuid);
}
