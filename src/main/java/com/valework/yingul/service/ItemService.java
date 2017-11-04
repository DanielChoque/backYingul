package com.valework.yingul.service;

import java.util.Set;

import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;

public interface ItemService {
	Yng_Item findByItemId(Long id);
	
	Yng_Item createItem(Yng_Item item, Set<Yng_ItemCategory> itemCategory);
}
