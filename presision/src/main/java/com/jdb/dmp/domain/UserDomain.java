package com.jdb.dmp.domain;

import java.util.Date;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
public class UserDomain {
    /**
     * 虚拟标的 的完成数目
     */
    private int virtualBidFinishAmount;

    /**
     * 用户注册时间
     */
    private Date registerDate;

    /**
     * 借钱买理财标的 的完成数目
     */
    private int borrowToBuyFinProdFinishAmount;

    public void setVirtualBidFinishAmount(int virtualBidAmount)
    {
        this.virtualBidFinishAmount = virtualBidAmount;
    }

    public int getVirtualBidFinishAmount()
    {
        return virtualBidFinishAmount;
    }

    public void setRegisterDate(Date registerDate)
    {
        this.registerDate = registerDate;
    }

    public Date getRegisterDate()
    {
        return registerDate;
    }

    public void setBorrowToBuyFinProdFinishAmount(int borrowToBuyFinProdFinishAmount)
    {
        this.borrowToBuyFinProdFinishAmount = borrowToBuyFinProdFinishAmount;
    }

    public int getBorrowToBuyFinProdFinishAmount()
    {
        return this.borrowToBuyFinProdFinishAmount;
    }
}
