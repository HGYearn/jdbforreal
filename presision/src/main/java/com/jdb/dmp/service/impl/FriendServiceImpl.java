package com.jdb.dmp.service.impl;

import com.jdb.dmp.dao.FriendMapper;
import com.jdb.dmp.domain.Friend;
import com.jdb.dmp.service.FriendService;
import com.jdb.dmp.util.PartitionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouqf on 16/10/9.
 */


@Service("friendService")
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    public List<Friend> find(String table_index, long id, String from_time, String end_time)
    {
        return friendMapper.find(table_index, id, from_time, end_time);
    }

    public void update(Friend friend)
    {
        friendMapper.update(friend);
    }

    public void insert(Friend friend)
    {
        friendMapper.insert(friend);
    }

    public void delete(String updateTime)
    {
        friendMapper.delete(updateTime);
    }
    public Friend getById(long id)
    {
        return friendMapper.getById(id);
    }
    public boolean exists(long id)
    {
        Friend friend = friendMapper.getById(id);
        if (friend != null)
            return true;
        else
            return false;
    }

    public List<Friend> getFriendsByUuid(String uuid) {
        int[] count = new int[] { 1024 };
        int[] length = new int[] { 1 };
        PartitionUtil pu = new PartitionUtil(count, length);
        long memberId = Long.valueOf(uuid);
        int tableIndex = pu.partition(memberId);
        return friendMapper.getFriendsByUuid(String.format("%04d", tableIndex), uuid);
    }

    public List<Friend> getNewFriends(String uuid) {
        return friendMapper.getNewFriends(uuid);
    }
}
