package com.meli.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.meli.dao.StatisticRepositoryDAO;
import com.meli.exception.CustomizedException;
import com.meli.model.CountryCodeInfo;
import com.meli.model.CountryInfo;
import com.meli.model.Statistic;
import com.meli.service.ConfigurationSettingService;
import com.meli.service.StatisticService;
import com.meli.utils.ConfigurationSettingConst;

@Service
public class StatisticServiceImpl implements StatisticService{
    
	@Autowired 
	StatisticRepositoryDAO srd;
	
	@Autowired 
	StatisticService ss;
	
	@Autowired
	private ConfigurationSettingService css;
	
	@Override
	public <S extends Statistic> Iterable<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public Optional<Statistic> findById(Integer id) {
		return srd.findById(id);
	}
	

	@Override
	public boolean existsById(Integer id) {
		return srd.existsById(id);
	}

	@Override
	public Iterable<Statistic> findAll() {
		return srd.findAll();
	}

	@Override
	public Iterable<Statistic> findAllById(Iterable<Integer> ids) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public void delete(Statistic entity) {
		
	}

	@Override
	public void deleteAll(Iterable<? extends Statistic> entities) {
		
	}

	@Override
	public void deleteAll() {
		
	}

	@Override
	public <S extends Statistic> S save(S entity) {
		return srd.save(entity);
	}
	
	public Statistic findByCountryCode(String countryCode) {
		return srd.findByCountryCode(countryCode);
	}

	public Statistic saveStatistic(CountryInfo ci, CountryCodeInfo cci) {
		try{
			String countryCode = ci.getCountryCode();
			Statistic st = srd.findByCountryCode(countryCode);
			List<Integer> latlng = cci.getLatlng();
			int distanceKilometers = calcDistance(Float.parseFloat(css.getKeys().get(ConfigurationSettingConst.latArg)), 
				Float.parseFloat(css.getKeys().get(ConfigurationSettingConst.longArg)), Integer.valueOf(latlng.get(0)), Integer.valueOf(latlng.get(1)));
			if(st!=null) {
				Integer invocations = st.getInvocations();
				invocations++;
				st.setInvocations(invocations);
				return ss.save(st);
			}else {
				st = new Statistic();
				st.setCountryCode(countryCode);
				st.setCountry(cci.getNativeName());
				st.setDistance(distanceKilometers);//km
				st.setInvocations(1);
				return ss.save(st);
			}
		}catch (Exception e) {
			throw new CustomizedException("An error has ocurred. Please contact DBA administrator");
		}
	}
	
	
	/**
	 * calcDistance
	 * @param latA
	 * @param longA
	 * @param latB
	 * @param longB
	 * @return int
	 */
	public static int calcDistance(float latA, Float longA, float latB, float longB) {
		double theDistance = (Math.sin(Math.toRadians(latA)) * Math.sin(Math.toRadians(latB)) + Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB)) * Math.cos(Math.toRadians(longA - longB))); 
		return new Double((Math.toDegrees(Math.acos(theDistance))) * 69.09*1.6093).intValue();
	}
}
