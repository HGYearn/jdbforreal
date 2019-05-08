package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.RepayFlow;
import com.jdb.dmp.model.RepayFlowBorrowCount;
import com.jdb.dmp.model.RepayFlowLendOutMaxRate;
import com.jdb.dmp.model.RepayFlowRecallMaxRate;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhouqf on 10/10/16.
 */
@Mapper
public interface RepayFlowMapper {
    @DataSource("bigdata2_jdb")
    @Select("SELECT * FROM repay_flow WHERE update_time >= #{from_time} and update_time < #{end_time} and id>#{id} and mod(id,#{mod})=#{shard} limit 1000")
    List<RepayFlow> find(@Param("from_time") String from_time,
                         @Param("end_time") String end_time,
                         @Param("id") long id,
                         @Param("mod") int mod,
                         @Param("shard") int shard);


    @DataSource("dmp")
    @Insert("INSERT INTO repay_flow (id,uuid,link_uuid,laundry_uuid,from_user,to_user,total_amount,amount,principal,interest,overdue_in" +
            "terest,rate,overdue_day,parent_repayed_amount,parent_repay_amount,sequence,pay_status,product_code,bid_uuid,tbid_uuid,interest_day," +
            "repay_amount,link_count,start_time,end_time,create_time,update_time,parent_repay_user,delay_interest,delay_day,overdue_rate,parent_uuid," +
            "product_create_time,law_status,law_time,call_version,original_principal,type,gurantee_uuid,original_interest,source_bid_uuid) values" +
            "(#{id},#{uuid},#{link_uuid},#{laundry_uuid},#{from_user},#{to_user},#{total_amount},#{amount},#{principal},#{interest}," +
            "#{overdue_interest},#{rate},#{overdue_day},#{parent_repayed_amount},#{parent_repay_amount},#{sequence},#{pay_status}," +
            "#{product_code},#{bid_uuid},#{tbid_uuid},#{interest_day},#{repay_amount},#{link_count},#{start_time},#{end_time},#{create_time}" +
            ",#{update_time},#{parent_repay_user},#{delay_interest},#{delay_day},#{overdue_rate},#{parent_uuid},#{product_create_time}," +
            "#{law_status},#{law_time},#{call_version},#{original_principal},#{type},#{gurantee_uuid},#{original_interest},#{source_bid_uuid})")
    void insert(RepayFlow repayFlow);

    @DataSource("dmp")
    @Insert("UPDATE repay_flow set uuid=#{uuid},link_uuid=#{link_uuid},laundry_uuid=#{laundry_uuid},from_user=#{from_user},to_user=#{to_user},total_amount=#{total_amount},amount=#{amount},principal=#{principal},interest=#{interest},overdue_in" +
            "terest=#{overdue_interest},rate=#{rate},overdue_day=#{overdue_day},parent_repayed_amount=#{parent_repayed_amount},parent_repay_amount=#{parent_repay_amount},sequence=#{sequence},pay_status=#{pay_status},product_code=#{product_code},bid_uuid=#{bid_uuid},tbid_uuid=#{tbid_uuid},interest_day=#{interest_day}," +
            "repay_amount=#{repay_amount},link_count=#{link_count},start_time=#{start_time},end_time=#{end_time},create_time=#{create_time},update_time=#{update_time},parent_repay_user=#{parent_repay_user},delay_interest=#{delay_interest},delay_day=#{delay_day},overdue_rate=#{overdue_rate},parent_uuid=#{parent_uuid}," +
            "product_create_time=#{product_create_time},law_status=#{law_status},law_time=#{law_time},call_version=#{call_version},original_principal=#{original_principal},type=#{type},gurantee_uuid=#{gurantee_uuid},original_interest=#{original_interest},source_bid_uuid=#{source_bid_uuid} where Id=#{id}")
    void update(RepayFlow repayFlow);

    @DataSource("dmp")
    @Delete("DELETE FROM repay_flow where update_time<#{update_time}")
    void delete(@Param("update_time") String update_time);

    @DataSource("dmp")
    @Select("SELECT * FROM repay_flow WHERE id=#{id}")
    RepayFlow getById(@Param("id") long id);

    @DataSource("dmp")
    @Select("SELECT * FROM repay_flow WHERE bid_uuid=#{bid}")
    RepayFlow getByBid(@Param("bid") String bid);

    @DataSource("dmp")
    @Select("SELECT * FROM repay_flow WHERE bid_uuid=#{uuid} and overdue_day > 0 and paystatus != 4")
    List<RepayFlow> getOverdue(@Param("uuid") String uuid);

    //A2
    @DataSource("dmp")
    @Select("select from_user as uuid, sum(original_principal) as borrowAmount " +
            "from repay_flow where sequence = 1 and type != 6 and create_time > #{threshDate} and " +
            " from_user in (${userList}) group by from_user")
    List<RepayFlowBorrowCount> getBorrowAmount(@Param("userList") String userList, @Param("threshDate") String threshDate);

    //A3
    @DataSource("dmp")
    @Select("select to_user as uuid, max(rate) as maxRate " +
            " from repay_flow where  overdue_day=0 and pay_status=1 and to_user in (${userList}) " +
            " and end_time >= #{threshDate} group by to_user")
    List<RepayFlowRecallMaxRate> getRecallMaxRate(@Param("userList") String userList, @Param("threshDate") String threshDate);

    //A4
    @DataSource("dmp")
    @Select("select to_user as uuid, max(rate) as maxRate " +
            "from repay_flow where sequence <= link_count and create_time > #{threshDate} " +
            "and to_user in (${userList}) group by to_user")
    List<RepayFlowLendOutMaxRate> getLendOutMaxRate(@Param("userList") String userList, @Param("threshDate") String threshDate);


}