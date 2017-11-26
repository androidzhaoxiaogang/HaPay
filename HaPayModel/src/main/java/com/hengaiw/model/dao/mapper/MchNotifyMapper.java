package com.hengaiw.model.dao.mapper;

import com.hengaiw.model.dao.model.MchNotify;
import com.hengaiw.model.dao.model.MchNotifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MchNotifyMapper {
    long countByExample(MchNotifyExample example);

    int deleteByExample(MchNotifyExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(MchNotify record);

    int insertSelective(MchNotify record);

    List<MchNotify> selectByExample(MchNotifyExample example);

    MchNotify selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") MchNotify record, @Param("example") MchNotifyExample example);

    int updateByExample(@Param("record") MchNotify record, @Param("example") MchNotifyExample example);

    int updateByPrimaryKeySelective(MchNotify record);

    int updateByPrimaryKey(MchNotify record);
}