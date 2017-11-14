package com.valework.yingul.controller;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.UserService;

@RestController
@RequestMapping("/sell")
public class SellController {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired 
	ItemService itemService;
	
	@Autowired 
	ServiceService serviceService;
	
	@Autowired
	BarrioDao barrioDao;
	
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
		//no es necesario setear las categorias en este punto
		/*Set<Yng_ItemCategory> itemCategoryTemp = new HashSet<>();
		Set<Yng_ItemCategory> itemCategory = service.getYng_Item().getItemCategory();
		for (Yng_ItemCategory s : itemCategory) {
			//LOG.info("eddy"+yng_Category.toString());
			itemCategoryTemp.add(new Yng_ItemCategory(item, categoryDao.findByName(s.getCategory().getName())));	    
		}*/
		LOG.info("eddy"+item.getItemCategory().toString());
		//para setear la ubicacion del item 
		Yng_Ubication ubicationTemp = new Yng_Ubication();
		ubicationTemp.setStreet(service.getYng_Item().getYng_Ubication().getStreet());
		ubicationTemp.setNumber(service.getYng_Item().getYng_Ubication().getNumber());
		ubicationTemp.setPostalCode(service.getYng_Item().getYng_Ubication().getPostalCode());
		ubicationTemp.setAditional(service.getYng_Item().getYng_Ubication().getAditional());
		//ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(service.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
		ubicationTemp.setYng_City(cityDao.findByCityId(service.getYng_Item().getYng_Ubication().getYng_City().getCityId()));
		ubicationTemp.setYng_Province(provinceDao.findByProvinceId(service.getYng_Item().getYng_Ubication().getYng_Province().getProvinceId()));
		/*if(service.getYng_Item().getYng_Ubication().getYng_Department().getName().toString()=="") {}
		else {ubicationTemp.setYng_Department(departmentDao.findByName(service.getYng_Item().getYng_Ubication().getYng_Department().getName()));}*/
        LOG.info(ubicationTemp.toString());
        //para setear el user aqui podemos modificar por el token 
        Yng_User userTemp= userDao.findByEmail(service.getYng_Item().getUser().getEmail());
        //LOG.info(userTemp.toString());
        item.setUser(userTemp);
        Yng_Ubication ubicationTempo=ubicationDao.save(ubicationTemp);
        LOG.info(ubicationTempo.toString());
        item.setYng_Ubication(ubicationTempo);
        itemService.save(item);
        //itemService.createItem(item, item.getItemCategory());
        Yng_Item temp = itemService.findByItemId(item.getItemId());
        service.setYng_Item(temp);
        serviceService.createService(service,service.getCobertureZone());
        return "save";
        

    }
}
