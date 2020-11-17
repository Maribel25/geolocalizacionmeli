package com.meli.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.meli.exception.CustomizedException;
import com.meli.model.CountryCodeInfo;
import com.meli.model.CountryInfo;
import com.meli.model.Currencies;
import com.meli.model.DTOApplicationErrorData;
import com.meli.model.FormModel;
import com.meli.model.Languages;
import com.meli.model.RatesData;
import com.meli.model.Statistic;
import com.meli.service.ConfigurationSettingService;
import com.meli.service.StatisticService;
import com.meli.utils.ConfigurationSettingConst;
import com.meli.utils.DateUtil;
import com.meli.utils.RestClient;

@Controller
public class HomeController {

	@Autowired
	private StatisticService statisticService;
	
	@Autowired
	private ConfigurationSettingService css;
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		model.addAttribute("formModel", new FormModel());
		return "home";
	}
	
	/**
	 * processForm
	 * @param <T>
	 * @param ip
	 * @param session
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/processForm", method=RequestMethod.POST)
	public String processForm(@ModelAttribute(value="ip") String ip, HttpSession session, Model model) {
		boolean matchesIp = ip.matches("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");
		try {
			if(!matchesIp)
				throw new CustomizedException("Please enter a correct ip");
			Gson gson = new Gson();
			StringBuilder sb = new StringBuilder();
			String dataFixerResponse = "";
			String geoResponse = RestClient.get(ip, css.getKeys().get(ConfigurationSettingConst.geoIp));
			CountryInfo countryInfo = gson.fromJson(geoResponse.toString(), CountryInfo.class);
			String countryInfoResponse = RestClient.get(countryInfo.getCountryCode(), css.getKeys().get(ConfigurationSettingConst.countryCodeInfo));
			CountryCodeInfo countryCodeInfo = gson.fromJson(countryInfoResponse, CountryCodeInfo.class);
			Currencies currencie = countryCodeInfo.getCurrencies().get(0);
			if(session.getAttribute("dataFixerResponse")!=null) {
				dataFixerResponse = (String) session.getAttribute("dataFixerResponse");
			}else {
				dataFixerResponse = RestClient.get(null, css.getKeys().get(ConfigurationSettingConst.accessKey), css.getKeys().get(ConfigurationSettingConst.dataFixerLatest));
				session.setAttribute("dataFixerResponse", dataFixerResponse);
			}
			Statistic savedSt = statisticService.saveStatistic(countryInfo, countryCodeInfo);
			buildResponse(ip, gson, sb, dataFixerResponse, countryCodeInfo, currencie, savedSt);
			model.addAttribute("report", sb.toString());
		}catch (CustomizedException e) {
			DTOApplicationErrorData applicationErrorData = e.getApplicationErrorData();
			model.addAttribute("report", applicationErrorData.getMessage());
		}catch (Exception e) {
			model.addAttribute("report", "An error has ocurred. Please contact system administrator");
		}
	  return "geoReport";
	}
	
	/**
	 * getStatistics
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/getStatisticReport", method=RequestMethod.GET)
	public String getStatistics(Model model) {
		List<Statistic> stLst = (List<Statistic>)statisticService.findAll();
		if(stLst!=null && stLst.size()>0) {
			Integer maxDistance = 0, promedio = 0;
			Integer minDistance = null;
			Integer totalInvocations = 0;
			Integer totalDistance = 0;
			String countryMax = "", countryMin = "";
			StringBuilder statitisticReport = new StringBuilder();
			for (Statistic statistic : stLst) {
				Integer stDistance = statistic.getDistance(); 
				totalDistance+=(statistic.getDistance()*statistic.getInvocations());
				totalInvocations += statistic.getInvocations();
				if(minDistance==null) {
					minDistance = stDistance;
					countryMin = statistic.getCountry();
				}
				if(stDistance<minDistance) {
					minDistance = stDistance;
					countryMin = statistic.getCountry();
				}
				if(stDistance>maxDistance) {
					maxDistance = stDistance;
					countryMax = statistic.getCountry();
				}
			}
			promedio = totalDistance / totalInvocations;
			statitisticReport.append("\u25CF Distancia más lejana a Buenos Aires " + maxDistance + " ("+countryMax+") <br>");
			statitisticReport.append("\u25CF Distancia más cercana a Buenos Aires " + minDistance + " ("+countryMin+") <br>");
			statitisticReport.append("\u25CF Distancia promedio de todas las ejecuciones " + promedio + " <br>");
			model.addAttribute("statistics", statitisticReport.toString());
		}else {
			model.addAttribute("statistics", "No statistics data to display");
		}
		return "statisticReport";
	}

	private void buildResponse(String ip, Gson gson, StringBuilder sb, String dataFixerResponse,
			CountryCodeInfo countryCodeInfo, Currencies currencie, Statistic savedSt) {
		RatesData rates = gson.fromJson(dataFixerResponse, RatesData.class);
		Languages language = countryCodeInfo.getLanguages().get(0);
		sb.append("> traceip " + ip +"<br>");
		sb.append("IP: " + ip + ", fecha actual: " + DateUtil.formatDate(new Date(), DateUtil.ddMMyyy) + " <br>");
		sb.append("País: " + countryCodeInfo.getNativeName() + " (" + countryCodeInfo.getName() + ")" + " <br>");
		sb.append("ISO Code: " + language.getIso639_1() + " <br>");
		sb.append("Idiomas: " + language.getNativeName() + " (" + language.getIso639_1() +")" + " <br>");
		sb.append("Moneda: " + currencie.getCode() + " (1 " + currencie.getCode() + " = " + rates.getRates().get("USD")+ " U$S)"  + " <br>");
		setHourLabel(sb, countryCodeInfo);
		sb.append("Distancia estimada: " + savedSt.getDistance() + " kms (" + css.getKeys().get(ConfigurationSettingConst.latArg) + ","+ css.getKeys().get(ConfigurationSettingConst.longArg)+") a " + "("+countryCodeInfo.getLatlng().get(0) +","+countryCodeInfo.getLatlng().get(1)+")");
	}

	/**
	 * buildHour
	 * @param sb
	 * @param countryCodeInfo
	 */
	private void setHourLabel(StringBuilder sb, CountryCodeInfo countryCodeInfo) {
		if(countryCodeInfo.getTimeZones()!=null && countryCodeInfo.getTimeZones().size()>0) {
			int size = countryCodeInfo.getTimeZones().size();
			for(int x=0; x<size; x++) {
				if(x==0)
					sb.append("Hora: ");
				else {
					if(x < size)
						sb.append(" o ");
			    }
				sb.append(DateUtil.getZonedTime(countryCodeInfo.getTimeZones().get(x)) + " ("+countryCodeInfo.getTimeZones().get(x)+") ");
			}
			sb.append("<br>");
		}
	}
}
