package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.Entry;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhouqf on 10/10/16.
 */
@Mapper
public interface EntryMapper {

    @DataSource("bigdata1_passport")
    @Select("SELECT * FROM entry_${table_index} WHERE id < #{id} order by id desc LIMIT 1000")
    List<Entry> find(@Param("table_index") String table_index,
                     @Param("id") long id);

    @DataSource("bigdata1_passport")
    @Select("SELECT * FROM entry_${table_index} order by id desc LIMIT 1")
    Entry getLast(@Param("table_index") String table_index);

    @DataSource("dmp")
    @Insert("INSERT INTO entry (id,UUID,entryId,entryLevel,entryMobile,entryPass,salt,entryUserName,entryIdentity,entryEmail,entryRegistTime," +
            "validateFlag,LastloginTime,entryImg,privacies,bind_card_status,id_encrypt,face_status,ac_code) values(#{id},#{UUID},#{entryId}," +
            "#{entryLevel},#{entryMobile},#{entryPass},#{salt},#{entryUserName},#{entryIdentity},#{entryEmail},#{entryRegistTime},#{validateFlag}," +
            "#{LastloginTime},#{entryImg},#{privacies},#{bind_card_status},#{id_encrypt},#{face_status},#{ac_code})")
    void insert(Entry entry);

    @DataSource("dmp")
    @Select("SELECT * FROM entry WHERE id=#{id}")
    Entry getById(@Param("id") long id);

    @DataSource("dmp")
    @Delete("DELETE FROM entry where entryRegistTime<#{entryRegistTime}")
    void delete(@Param("entryRegistTime") String entryRegistTime);

    @DataSource("dmp")
    @Select("SELECT * FROM entry WHERE uuid=#{uuid}")
    Entry getByUuid(@Param("uuid") String uuid);

    @DataSource("dmp")
    @Select("SELECT * FROM entry")
    List<Entry> getAll();
}