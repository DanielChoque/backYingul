package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Category;

public interface CategoryDao extends CrudRepository<Yng_Category, Long>{
	Yng_Category findByName(String name);
}

