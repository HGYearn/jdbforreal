package com.jdb.dmp.dao;

import com.jdb.dmp.model.PhoneList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by qimwang on 11/3/16.
 */
@Mapper
public interface PhoneListMapper {
    @Select("select entryMobile from phone_list_#{tableIndex} where entryUuid = #{uuid}")
    List<PhoneList> getContactsByUuid(@Param("tableIndex") String tableIndex, @Param("uuid") String uuid);
}