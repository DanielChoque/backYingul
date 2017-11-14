package com.valework.yingul.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.model.Yng_Barrio;
import com.valework.yingul.model.Yng_City;
import com.valework.yingul.model.Yng_Province;
import com.valework.yingul.service.BarrioService;
import com.valework.yingul.service.CityService;
import com.valework.yingul.service.ProvinceService;

@RestController
public class UbicationController {
	
	@Autowired
    private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private BarrioService barrioService;
	
	@RequestMapping("/province/all")
    public List<Yng_Province> findProvinceList() {
        List<Yng_Province> provinceList = provinceService.findAll();
        return provinceList;
    }
    @RequestMapping("/city/{provinceId}")
    public List<Yng_City> findCityListByProvince(@PathVariable("provinceId") int provinceId) {
    	Yng_Province yng_Province = provinceService.findByProvinceId(provinceId);
        List<Yng_City> cityList = cityService.findByProvince(yng_Province);
        return cityList;
    }
    @RequestMapping("/barrio/{cityId}")
    public List<Yng_Barrio> findBarrioListByCity(@PathVariable("cityId") int cityId) {
    	Yng_City yng_City = cityService.findByCityId(cityId);
        List<Yng_Barrio> barrioList = barrioService.findByCity(yng_City);
        return barrioList;
    }
    
    
}
