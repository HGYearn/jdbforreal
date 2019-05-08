package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.model.AutoLendCreditAmount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by qimwang on 11/3/16.
 */
@Mapper
public interface AutoLendCreditMapper {
    //A5
    @DataSource("dmp")
    @Select("select friend_uuid as uuid, sum(credit_quota) as maxCreditAmount from auto_lendout_credit " +
            " where credit_date > #{threshDate} and credit_status = 1 and friend_uuid in (${userList}) group by friend_uuid")
    List<AutoLendCreditAmount> getAutoLendCreditAmount(@Param("userList") String userList, @Param("threshDate") String threshDate);

}
