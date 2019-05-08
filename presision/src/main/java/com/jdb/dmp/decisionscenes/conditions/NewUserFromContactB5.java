package com.jdb.dmp.decisionscenes.conditions;

import com.jdb.dmp.domain.JdbUser;

/**
 * Created by qimwang on 11/3/16.
 */
public class NewUserFromContactB5 implements Condition {
    public Boolean isSatisfied(JdbUser jdbUser) {
        if (jdbUser.getCurrNewContactNameB5() != null) {
            return true;
        } else {
            return false;
        }
    }
}
