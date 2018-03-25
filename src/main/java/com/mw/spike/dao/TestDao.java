package com.mw.spike.dao;

import com.mw.spike.domain.SpikeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author WangCH
 * @create 2018-03-08 18:31
 */
@Mapper
public interface TestDao {


    @Select("select * from spike_order order by id limit #{start},10")
    List<SpikeOrder> getSpikeOrderTest(@Param("start") int start);

}
