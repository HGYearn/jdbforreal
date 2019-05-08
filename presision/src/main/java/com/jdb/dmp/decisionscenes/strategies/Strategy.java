package com.jdb.dmp.decisionscenes.strategies;

import com.jdb.dmp.decisionscenes.conditions.Condition;

import java.io.Serializable;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/11
 */
public interface Strategy extends Serializable {
    /**
     * 获得这个策略的所有条件
     *
     * @return List
     */
    List<Condition> getConditions();
}
