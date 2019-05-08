package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by qimwang on 11/3/16.
 */
@Mapper
public interface PhoneIncMapper {
    @DataSource("ur")
    @Select("SELECT ta_tel as taTel FROM phone_inc_${tableIndex} WHERE my_tel = #{myTel}")
    List<String> find(@Param("tableIndex") String tableIndex, @Param("myTel") String myTel);
}
