package com.jdb.dmp.decisionscenes.conditions;

import com.jdb.dmp.domain.JdbUser;

/**
 * Created by qimwang on 11/3/16.
 */
public class NewFriendFromContactB3 implements Condition {

    public NewFriendFromContactB3() {
    }

    public Boolean isSatisfied(JdbUser jdbUser) {
        if (jdbUser.getCurrNewContactNameB3() != null) {
            return true;
        } else {
            return false;
        }
    }
}
