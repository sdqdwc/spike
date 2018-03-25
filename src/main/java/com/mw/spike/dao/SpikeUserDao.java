package com.mw.spike.dao;

import com.mw.spike.domain.SpikeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SpikeUserDao {

    @Select("select * from spike_user where id = #{id}")
    SpikeUser getById(@Param("id") Long id);

    @Update("update spike_user set password = #{password} where id = #{id}")
    void update(SpikeUser toBeUpdate);
}
