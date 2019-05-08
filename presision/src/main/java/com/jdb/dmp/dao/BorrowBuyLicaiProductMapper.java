package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.BorrowBuyLicaiProduct;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhouqf on 10/10/16.
 */
@Mapper
public interface BorrowBuyLicaiProductMapper {

    @DataSource("bigdata2_jdb")
    @Select("SELECT * FROM borrowbuy_licai_product limit #{from}, #{offset}")
    List<BorrowBuyLicaiProduct> find(@Param("from") int from,
                     @Param("offset") int offset);

    @DataSource("dmp")
    @Insert("INSERT INTO borrowbuy_licai_product (product_uuid,entry_uuid) values(#{product_uuid},#{entry_uuid})")
    void insert(BorrowBuyLicaiProduct borrowBuyLicaiProduct);

    @DataSource("dmp")
    @Select("SELECT * FROM borrowbuy_licai_product WHERE product_uuid=#{product_uuid}")
    BorrowBuyLicaiProduct getById(@Param("product_uuid") String product_uuid);

    @DataSource("dmp")
    @Select("SELECT * FROM borrowbuy_licai_product WHERE entry_uuid=#{entry_uuid}")
    BorrowBuyLicaiProduct getByUuid(@Param("entry_uuid") String entry_uuid);
}