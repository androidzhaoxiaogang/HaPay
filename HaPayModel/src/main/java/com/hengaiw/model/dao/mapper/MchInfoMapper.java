package com.hengaiw.model.dao.mapper;

import com.hengaiw.model.dao.model.MchInfo;
import com.hengaiw.model.dao.model.MchInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MchInfoMapper {
    long countByExample(MchInfoExample example);

    int deleteByExample(MchInfoExample example);

    int deleteByPrimaryKey(@Param("id") Integer id, @Param("mchId") String mchId);

    int insert(MchInfo record);

    int insertSelective(MchInfo record);

    List<MchInfo> selectByExampleWithBLOBs(MchInfoExample example);

    List<MchInfo> selectByExample(MchInfoExample example);

    MchInfo selectByPrimaryKey(@Param("id") Integer id, @Param("mchId") String mchId);

    int updateByExampleSelective(@Param("record") MchInfo record, @Param("example") MchInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") MchInfo record, @Param("example") MchInfoExample example);

    int updateByExample(@Param("record") MchInfo record, @Param("example") MchInfoExample example);

    int updateByPrimaryKeySelective(MchInfo record);

    int updateByPrimaryKeyWithBLOBs(MchInfo record);

    int updateByPrimaryKey(MchInfo record);
    
    MchInfo selectByMchId(@Param("mchId") String mchId);
}