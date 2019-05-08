package com.jdb.dmp.service;

import com.jdb.dmp.domain.JdbUser;
import com.jdb.dmp.domain.Result;

/**
 * Created by qimwang on 10/11/16.
 */
public interface IRepayService {
    Result getBuyLicaiProduct(JdbUser user);
}