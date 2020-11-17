package com.meli.service;

import org.springframework.data.repository.CrudRepository;

import com.meli.model.CountryCodeInfo;
import com.meli.model.CountryInfo;
import com.meli.model.Statistic;

public interface StatisticService extends CrudRepository<Statistic, Integer>{

	Statistic findByCountryCode(String countryCode);
	Statistic saveStatistic(CountryInfo ci, CountryCodeInfo cci);
	
}
