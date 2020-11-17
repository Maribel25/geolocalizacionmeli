package com.meli.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.meli.dao.ConfigurationSettingRepositoryDAO;
import com.meli.model.ConfigurationSetting;
import com.meli.service.ConfigurationSettingService;

@Service
@Configurable
public class ConfigurationSettingImp implements ConfigurationSettingService{
	
	public static Map<String, String> keys;
	
	@Autowired 
	ConfigurationSettingRepositoryDAO csrd;
	
	public Map<String,String> getKeys() {
		if(keys == null) {
			List<ConfigurationSetting> cfLst = (List<ConfigurationSetting>)csrd.findAll();
			if(cfLst!=null && cfLst.size()>0) {
				keys = new HashMap<String, String>();
				for (ConfigurationSetting cs : cfLst) {
					keys.put(cs.getKey(), cs.getValue());
				}
			}
		}
		return keys;
	}
}
