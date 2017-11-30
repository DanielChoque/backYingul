package com.valework.yingul.dao;


import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.Yng_Motorized;


public interface MotorizedDao extends CrudRepository<Yng_Motorized, Long>{
	Yng_Motorized findByMotorizedId(Long id);

}
