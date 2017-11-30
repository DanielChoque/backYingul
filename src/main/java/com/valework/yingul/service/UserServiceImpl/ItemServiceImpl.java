package com.valework.yingul.service.UserServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.UserService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService{
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public Yng_Item findByItemId(Long id) {
		return itemDao.findByItemId(id);
	}

	@Override
	public Yng_Item createItem(Yng_Item item, Set<Yng_ItemCategory> itemCategory) {
		Yng_Item localItem = itemDao.findByItemId(item.getItemId());

        if (localItem != null) {
            LOG.info("User with username {} already exist. Nothing will be done. ", item.getName());
        } else {
            for (Yng_ItemCategory ur : itemCategory) {
                categoryDao.save(ur.getCategory());
            }
            item.getItemCategory().addAll(itemCategory);
            
            localItem = itemDao.save(item);
        }

        return localItem;
	}

	public List<Yng_Item> findAll() {
		// TODO Auto-generated method stub
		return itemDao.findAll();
	}

	public void save(Yng_Item yng_item) {
		itemDao.save(yng_item);
		
	}

	public List<Yng_Item> findByUser(Yng_User yng_user) {
		Long userId = yng_user.getUserId();
		List<Yng_Item> itemList = itemDao.findAll().stream() 			//convert list to stream
                .filter(city -> userId==city.getUser().getUserId())	//filters the line, equals to username
                .collect(Collectors.toList());
        return itemList;
	}	
	
}
