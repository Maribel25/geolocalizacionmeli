package com.meli.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meli.model.Statistic;

@Repository
public interface StatisticRepositoryDAO extends JpaRepository<Statistic, Integer>{

	Statistic findByCountryCode(String countryCode);
}
