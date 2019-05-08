package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.AutoLendOutCredit;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhouqf on 10/10/16.
 */
@Mapper
public interface AutoLendOutCreditMapper {

    @DataSource("bigdata2_jdb")
    @Select("SELECT * FROM auto_lendout_credit WHERE credit_date >#{from_time} AND credit_date < #{end_time} and id > #{id} order by id asc LIMIT 1000")
    List<AutoLendOutCredit> find(@Param("from_time") String from_time,
                         @Param("end_time") String end_time,
                         @Param("id") int id);

    @DataSource("dmp")
    @Insert("INSERT INTO auto_lendout_credit (id,uuid,entry_uuid,friend_uuid,create_time,update_time,credit_status," +
            "credit_quota,used_quota,min_rate,term_type,term_from,term_to," +
            "credit_date,credit_auto_end_date,credit_end_date,checked_time,create_contract_flag,source_from) values" +
            "(#{id},#{uuid},#{entry_uuid},#{friend_uuid},#{create_time},#{update_time},#{credit_status},#{credit_quota}," +
            "#{used_quota},#{min_rate},#{term_type},#{term_from},#{term_to},#{credit_date},#{credit_auto_end_date}," +
            "#{credit_end_date},#{checked_time},#{create_contract_flag},#{source_from})")
    void insert(AutoLendOutCredit autoLendOutCredit);

    @DataSource("dmp")
    @Insert("UPDATE auto_lendout_credit set uuid=#{uuid},entry_uuid=#{entry_uuid},friend_uuid=#{friend_uuid},create_time=#{create_time}," +
            "update_time=#{update_time},credit_status=#{credit_status},credit_quota=#{credit_quota},used_quota=#{used_quota}," +
            "min_rate=#{min_rate},term_type=#{term_type}," +
            "term_from=#{term_from},term_to=#{term_to},credit_date=#{credit_date},credit_auto_end_date=#{credit_auto_end_date}," +
            "credit_end_date=#{credit_end_date},checked_time=#{checked_time},create_contract_flag=#{create_contract_flag}," +
            "source_from=#{source_from} where Id=#{id}")
    void update(AutoLendOutCredit autoLendOutCredit);

    @DataSource("dmp")
    @Delete("DELETE FROM auto_lendout_credit where credit_date<#{credit_date}")
    void delete(@Param("credit_date") String credit_date);

    @DataSource("dmp")
    @Select("SELECT * FROM auto_lendout_credit WHERE id=#{id}")
    AutoLendOutCredit getById(@Param("id") int id);
}