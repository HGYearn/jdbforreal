package com.jdb.dmp.decisionscenes.conditions;


import com.jdb.dmp.domain.JdbUser;

import java.io.Serializable;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
public interface Condition extends Serializable{

    Boolean isSatisfied(JdbUser jdbUser);
}
