package com.valework.yingul.service;

import java.util.List;
import java.util.Set;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_User;

public interface ItemService {
	List<Yng_Item> findAll();
	Yng_Item findByItemId(Long id);
	Yng_Item createItem(Yng_Item item, Set<Yng_ItemCategory> itemCategory);
	void save (Yng_Item yng_item);
	List<Yng_Item> findByUser(Yng_User yng_User);
}
