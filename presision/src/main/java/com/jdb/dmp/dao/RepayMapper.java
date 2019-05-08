package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.BidResult;
import com.jdb.dmp.domain.RepayResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by qimwang on 10/11/16.
 */
@Mapper
public interface RepayMapper {
    @DataSource("dmp")
    @Select("SELECT product_uuid as productUuid, entry_uuid as entryUuid FROM borrowbuy_licai_product WHERE entry_uuid = #{uuid}")
    RepayResult getProduct(@Param("uuid") String uuid);

    @DataSource("dmp")
    @Select("SELECT count(id) as bidCount FROM repay_flow WHERE bid_uuid = #{productId}")
    BidResult getBidCount(@Param("productId") String productId);
}
