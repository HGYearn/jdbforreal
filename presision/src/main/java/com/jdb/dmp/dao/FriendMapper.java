package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.Friend;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhouqf on 10/10/16.
 */
@Mapper
public interface FriendMapper {
    @DataSource("bigdata1_jdb")
    @Select("SELECT * FROM friend_${table_index} WHERE id > #{id} and updateTime >=#{from_time} and updateTime <#{end_time} LIMIT 1000")
    List<Friend> find(@Param("table_index") String table_index,
                      @Param("id") long id,
                      @Param("from_time") String from_time,
                      @Param("end_time") String end_time);

    @DataSource("bigdata1_jdb")
    @Select("select * from friend_${tableIndex} where entryUuid = #{uuid}")
    List<Friend> getFriendsByUuid(@Param("tableIndex") String tableIndex, @Param("uuid") String uuid);

    @DataSource("dmp")
    @Insert("INSERT INTO friend (id,remark,friendId,entryUuid,createTime,updateTime,valiableFlag,readFlag,source,type,allowWatchMe,heWatchMe," +
            "watchHim,heAllowWatch,remarkName,tags,followStatus,ext,followTime,fansTime) values(#{id},#{remark},#{friendId},#{entryUuid}," +
            "#{createTime},#{updateTime},#{valiableFlag},#{readFlag},#{source},#{type},#{allowWatchMe},#{heWatchMe},#{watchHim},#{heAllowWatch}," +
            "#{remarkName},#{tags},#{followStatus},#{ext},#{followTime},#{fansTime})")
    void insert(Friend friend);

    @DataSource("dmp")
    @Update("UPDATE friend SET remark=#{remark},friendId=#{friendId},entryUuid=#{entryUuid},createTime=#{createTime},updateTime=#{updateTime}," +
            "valiableFlag=#{valiableFlag},readFlag=#{readFlag},source=#{source},type=#{type},allowWatchMe=#{allowWatchMe},heWatchMe=#{heWatchMe}," +
            "watchHim=#{watchHim},heAllowWatch=#{heAllowWatch},remarkName=#{remarkName},tags=#{tags},followStatus=#{followStatus},ext=#{ext}," +
            "followTime=#{followTime},fansTime=#{fansTime} WHERE id=#{id}")
    void update(Friend friend);


    @DataSource("dmp")
    @Delete("DELETE FROM friend where updateTime<#{updateTime}")
    void delete(@Param("updateTime") String updateTime);

    @DataSource("dmp")
    @Select("SELECT * FROM friend WHERE id=#{id}")
    Friend getById(@Param("id") long id);

    @DataSource("dmp")
    @Select("SELECT * FROM friend WHERE entryUuid = #{uuid}")
    List<Friend> getNewFriends(@Param("uuid") String uuid);
}
