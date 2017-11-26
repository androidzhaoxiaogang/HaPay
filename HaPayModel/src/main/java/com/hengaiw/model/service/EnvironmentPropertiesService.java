package com.hengaiw.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hengaiw.model.dao.mapper.CloudConfigMapper;
import com.hengaiw.model.dao.model.CloudConfig;

@Component
public class EnvironmentPropertiesService {
	@Autowired
	private CloudConfigMapper applicationConfigMapper;

	@Transactional
	public Map<String, String> get(String applicationName, String profile) {
		List<CloudConfig> list = applicationConfigMapper.selectByprofile(applicationName);
		for (CloudConfig cloudConfig : list) {
			System.out.println("应用名[" + applicationName + "]-环境[" + profile + "]-" + "属性值:"
					+ cloudConfig.getCloud_config_dev_value());
		}
		System.out.println();
		Map<String, String> result = null;
		if (list != null && list.size() > 0) {
			result = new HashMap<String, String>();
			for (CloudConfig cc : list) {
				if (Objects.equals("test", profile)) {
					result.put(cc.getCloud_config_key(), cc.getCloud_config_test_value());
				} else if (Objects.equals("prod", profile)) {
					result.put(cc.getCloud_config_key(), cc.getCloud_config_prod_value());
				} else if (Objects.equals("dev", profile)) {
					result.put(cc.getCloud_config_key(), cc.getCloud_config_dev_value());
				}
			}
		}
		return result;
	}
}
