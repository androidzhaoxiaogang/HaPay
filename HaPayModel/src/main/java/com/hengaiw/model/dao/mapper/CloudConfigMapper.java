package com.hengaiw.model.dao.mapper;

import com.hengaiw.model.dao.model.CloudConfig;
import com.hengaiw.model.dao.model.CloudConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CloudConfigMapper {
    long countByExample(CloudConfigExample example);

    int deleteByExample(CloudConfigExample example);

    int deleteByPrimaryKey(Integer cloud_config_id);

    int insert(CloudConfig record);

    int insertSelective(CloudConfig record);

    List<CloudConfig> selectByExample(CloudConfigExample example);

    CloudConfig selectByPrimaryKey(Integer cloud_config_id);

    int updateByExampleSelective(@Param("record") CloudConfig record, @Param("example") CloudConfigExample example);

    int updateByExample(@Param("record") CloudConfig record, @Param("example") CloudConfigExample example);

    int updateByPrimaryKeySelective(CloudConfig record);

    int updateByPrimaryKey(CloudConfig record);
    
    List<CloudConfig> selectByprofile(String name);
    
}