package com.jdb.dmp.decisionscenes.conditions;

import com.jdb.dmp.domain.JdbUser;

/**
 * Created by qimwang on 11/3/16.
 */
public class NewFriendB2 implements Condition {
    public Boolean isSatisfied(JdbUser jdbUser) {
        if (!jdbUser.getCurrNewFriendNameB2().equalsIgnoreCase("TA")) {
            return true;
        } else {
            return false;
        }
    }
}
