package com.valework.yingul.service;

import java.util.List;
import com.valework.yingul.model.Yng_City;
import com.valework.yingul.model.Yng_Province;

public interface CityService {
	List<Yng_City> findByProvince(Yng_Province yng_province);
	List<Yng_City> findByProvince2(int cp);
	List<Yng_City> findByCP(String cp);
	Yng_City findByCityId(int cityId);
	
}
