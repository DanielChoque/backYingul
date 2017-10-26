package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Province;

public interface ProvinceDao extends CrudRepository<Yng_Province, Long>{
	Yng_Province findByName(String name);
}
