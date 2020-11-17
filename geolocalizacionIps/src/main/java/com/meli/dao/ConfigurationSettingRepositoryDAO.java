package com.meli.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meli.model.ConfigurationSetting;

@Repository
public interface ConfigurationSettingRepositoryDAO extends JpaRepository<ConfigurationSetting, String>{

}
