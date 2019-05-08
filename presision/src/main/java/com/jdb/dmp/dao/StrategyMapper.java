package com.jdb.dmp.dao;

import com.jdb.dmp.datasource.DataSource;
import com.jdb.dmp.domain.StrategyDomain;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
@Mapper
public interface StrategyMapper {

    @DataSource("dmp")
    @Select("select * from dmp_strategy where layer_type = ${layerType}")
    List<StrategyDomain> getByLayerType(@Param("layerType") int layerType);

    @DataSource("dmp")
    @Insert("insert into dmp_strategy (strategy, layer_type, time_created) values (#{strategy}, #{layer_type}, #{time_created})")
    Boolean addOne(@Param("strategy") byte[] strategy, @Param("layer_type") int layer_type, @Param("time_created") Date time_created);
}
