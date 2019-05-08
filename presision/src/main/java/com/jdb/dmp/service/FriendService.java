package com.jdb.dmp.service;

import com.jdb.dmp.domain.Friend;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */
public interface FriendService {

    List<Friend> find(String table_index, long id, String from_time, String end_time);

    void insert(Friend friend);

    void update(Friend friend);

    void delete(String updateTime);

    Friend getById(long id);

    boolean exists(long id);

    List<Friend> getFriendsByUuid(String uuid);

    List<Friend> getNewFriends(String uuid);
}
