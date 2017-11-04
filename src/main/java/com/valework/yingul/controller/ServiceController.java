package com.valework.yingul.controller;

import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;

import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.ItemService;

import com.valework.yingul.service.UserService;

@Controller
public class ServiceController {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired 
	ItemService itemService;
	
	@Autowired 
	CityDao cityDao;
	
	@Autowired 
	ProvinceDao provinceDao;
	
	@Autowired
	DepartmentDao departmentDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired 
	ServiceDao serviceDao;
	
	@Autowired 
	UbicationDao ubicationDao;
	
	@RequestMapping(value = "/service", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String servicePost(@RequestBody Yng_Service service) throws JSONException {
		Yng_Item item = service.getYng_Item();
		
		//para setear las categorias 
		Set<Yng_ItemCategory> itemCategoryTemp = new HashSet<>();
		Set<Yng_ItemCategory> itemCategory = service.getYng_Item().getItemCategory();
		for (Yng_ItemCategory s : itemCategory) {
			//LOG.info("eddy"+yng_Category.toString());
			itemCategoryTemp.add(new Yng_ItemCategory(item, categoryDao.findByName(s.getCategory().getName())));	    
		}
		//LOG.info("eddy"+itemCategoryTemp.toString());
		//para setear la ubicacion del item 
		Yng_Ubication ubicationTemp = new Yng_Ubication();
		ubicationTemp.setAddress(service.getYng_Item().getYng_Ubication().getAddress());
		ubicationTemp.setYng_City(cityDao.findByName(service.getYng_Item().getYng_Ubication().getYng_City().getName()));
		ubicationTemp.setYng_Province(provinceDao.findByName(service.getYng_Item().getYng_Ubication().getYng_Province().getName()));
		ubicationTemp.setYng_Department(departmentDao.findByName(service.getYng_Item().getYng_Ubication().getYng_Department().getName()));
        //LOG.info(ubicationTemp.toString());
        //para setear el user aqui podemos modificar por el token 
        Yng_User userTemp= userDao.findByEmail(service.getYng_Item().getUser().getEmail());
        //LOG.info(userTemp.toString());
        item.setUser(userTemp);
        Yng_Ubication ubicationTempo=ubicationDao.save(ubicationTemp);
        LOG.info(ubicationTempo.toString());
        item.setYng_Ubication(ubicationTempo);
        itemService.createItem(item, itemCategoryTemp);
        Yng_Item temp = itemService.findByItemId(item.getItemId());
        service.setYng_Item(temp);
        //LOG.info(service.toString());
        if(serviceDao.save(service) != null){
        	return "save";
        }
        else {
        	return "error";
        } 
    }
	
}
