package com.meli.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CountryCodeInfo {
	
	private String name;
	
	private String nativeName;
	
	private String region;
	
	private String capital;
	
	@SerializedName("timezones")
	private List<String> timeZones;
	
	private List<Currencies> currencies;
	
	private List<Languages> languages;
	
	private List<Integer> latlng;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNativeName() {
		return nativeName;
	}
	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}
	public List<Currencies> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<Currencies> currencies) {
		this.currencies = currencies;
	}
	public List<Languages> getLanguages() {
		return languages;
	}
	public void setLanguages(List<Languages> languages) {
		this.languages = languages;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public List<String> getTimeZones() {
		return timeZones;
	}
	public void setTimeZones(List<String> timeZones) {
		this.timeZones = timeZones;
	}
	public List<Integer> getLatlng() {
		return latlng;
	}
	public void setLatlng(List<Integer> latlng) {
		this.latlng = latlng;
	}

}
