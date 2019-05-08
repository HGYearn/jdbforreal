package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.VirtualRepayFlow;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhouqf on 10/10/16.
 */
@Mapper
public interface VirtualRepayFlowMapper {
    @DataSource("bigdata2_jdb")
    @Select("SELECT * FROM virtual_repay_flow WHERE update_time >= #{from_time} and update_time < #{end_time} and id>#{id} limit 1000")
    List<VirtualRepayFlow> find(@Param("from_time") String from_time,
                         @Param("end_time") String end_time,
                         @Param("id") long id);


    @DataSource("dmp")
    @Insert("INSERT INTO virtual_repay_flow (id,uuid,virtual_productid,to_user,amount,interest,repay_status,repay_time,create_time,update_time) values" +
            "(#{id},#{uuid},#{virtual_productid},#{to_user},#{amount},#{interest},#{repay_status},#{repay_time},#{create_time},#{update_time})")
    void insert(VirtualRepayFlow virtualRepayFlow);

    @DataSource("dmp")
    @Delete("DELETE FROM repay_flow where update_time<#{update_time}")
    void delete(@Param("update_time") String update_time);

    @DataSource("dmp")
    @Select("SELECT * FROM virtual_repay_flow WHERE id=#{id}")
    VirtualRepayFlow getById(@Param("id") long id);


    @DataSource("dmp")
    @Select("SELECT * FROM virtual_repay_flow WHERE uuid=#{uuid}")
    VirtualRepayFlow getByUuid(@Param("uuid") String uuid);
}