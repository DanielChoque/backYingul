package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Item;

public interface ItemDao extends CrudRepository<Yng_Item, Long> {
	Yng_Item findByItemId(Long id);
}
