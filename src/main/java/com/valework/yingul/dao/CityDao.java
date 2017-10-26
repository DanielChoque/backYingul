package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_City;

public interface CityDao extends CrudRepository<Yng_City, Long>{
	Yng_City findByName(String name);
}
