package com.valework.yingul.controller;

import java.util.HashSet;
import java.util.Set;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.BarrioDao;
import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.DepartmentDao;
import com.valework.yingul.dao.ItemCategoryDao;
import com.valework.yingul.dao.ItemImageDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.dao.ServiceProvinceDao;
import com.valework.yingul.dao.UbicationDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_ItemImage;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_ServiceProvince;
import com.valework.yingul.model.Yng_Ubication;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.ItemService;
import com.valework.yingul.service.S3Services;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.UserServiceImpl.S3ServicesImpl;
import com.valework.yingul.service.StorageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/sell")
public class SellController {
	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired 
	ItemImageDao itemImageDao;
	
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
	
	@Autowired
	StorageService storageService;

	@Autowired
	ItemCategoryDao itemCategoryDao; 
	
	@Autowired
	ServiceProvinceDao serviceProvinceDao;
	@Autowired
	S3Services s3Services;

	@RequestMapping(value = "/service", method = RequestMethod.POST)
	@ResponseBody
    public String sellServicePost(@Valid @RequestBody Yng_Service service) throws MessagingException {	
		String ruta="Servicios";
		Yng_Service serviceTemp = service;
		Yng_Item itemTemp=serviceTemp.getYng_Item();
		//obtenemos la lista de las imagenes 
		Set<Yng_ItemImage> itemImage=serviceTemp.getYng_Item().getItemImage();
		//borramos las imagenes 
		serviceTemp.getYng_Item().setItemImage(null);
		//obtenemos la lista de categorias
		Set<Yng_ItemCategory> itemCategory = new HashSet<>();
		itemCategory=serviceTemp.getYng_Item().getItemCategory();
		//borramos la lista de cagorias para que no se inserte dos veces
		serviceTemp.getYng_Item().setItemCategory(null);
		//para setear la ubicacion del item 
		Yng_Ubication ubicationTemp = new Yng_Ubication();
		ubicationTemp.setStreet(serviceTemp.getYng_Item().getYng_Ubication().getStreet());
		ubicationTemp.setNumber(serviceTemp.getYng_Item().getYng_Ubication().getNumber());
		ubicationTemp.setPostalCode(serviceTemp.getYng_Item().getYng_Ubication().getPostalCode());
		ubicationTemp.setAditional(serviceTemp.getYng_Item().getYng_Ubication().getAditional());
		ubicationTemp.setYng_Province(provinceDao.findByProvinceId(serviceTemp.getYng_Item().getYng_Ubication().getYng_Province().getProvinceId()));
		ubicationTemp.setYng_City(cityDao.findByCityId(serviceTemp.getYng_Item().getYng_Ubication().getYng_City().getCityId()));	
		ubicationTemp.setYng_Barrio(barrioDao.findByBarrioId(serviceTemp.getYng_Item().getYng_Ubication().getYng_Barrio().getBarrioId()));
        Yng_Ubication ubicationTempo=ubicationDao.save(ubicationTemp);
        itemTemp.setYng_Ubication(ubicationTempo);
		//para setear el usuario
		Yng_User userTemp= userDao.findByUsername(itemTemp.getUser().getUsername());
		userTemp.setPhone(itemTemp.getUser().getPhone());
		userTemp.setPhone2(itemTemp.getUser().getPhone2());
		userTemp.setWebSite(itemTemp.getUser().getWebSite());
		userDao.save(userTemp);
		itemTemp.setUser(userTemp);
		//hasta aqui para el usuario
		//volvemos la imagen nulo para que no guarde en la base de datos
		String image=itemTemp.getPrincipalImage();
		itemTemp.setPrincipalImage("");
		itemService.save(itemTemp);
        Yng_Item temp = itemService.findByItemId(itemTemp.getItemId());
        for (Yng_ItemCategory s : itemCategory) {
        	ruta= ruta +"/"+s.getCategory().getName();
        	s.setItem(temp);
        	itemCategoryDao.save(s);	    
		}
        //imagen principal
		
		logger.info(image);
		String extension=image.substring(11,14);
		if(image.charAt(13)=='e') {
			extension="jpeg";
		}
		String nombre="principal"+temp.getItemId();
		logger.info(extension);
		byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
		s3Services.uploadFile(nombre,extension, bI);
		nombre=nombre+"."+extension;   
		temp.setPrincipalImage(nombre);
		itemService.save(temp);
		int k=0;
		for (Yng_ItemImage st : itemImage) {
        	k++;
        	image=st.getImage();
    		st.setImage("");
    		extension=image.substring(11,14);
    		if(image.charAt(13)=='e') {
    			extension="jpeg";
    		}
    		nombre="img"+k+temp.getItemId();
    		logger.info(extension);
    		bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
    		s3Services.uploadFile(nombre,extension, bI);
    		nombre=nombre+"."+extension;   
    		st.setImage(nombre);
    		st.setItem(temp);	
        	itemImageDao.save(st);	    
		}
		  
        serviceTemp.setYng_Item(temp);
        //obtenemos la lista de provincia de la zona de cobertura
  		Set<Yng_ServiceProvince> serviceProvince = new HashSet<>();
  		serviceProvince=serviceTemp.getCobertureZone();
  		//borramos la lista de cagorias para que no se inserte dos veces
  		serviceTemp.setCobertureZone(null);
        Yng_Service serz = serviceDao.save(serviceTemp);
        for (Yng_ServiceProvince si : serviceProvince) {
        	si.setProvince(provinceDao.findByProvinceId(si.getProvince().getProvinceId()));
        	si.setService(serz);
        	serviceProvinceDao.save(si);	    
		}
        try {
			smtpMailSender.send(userTemp.getEmail(), "Servicio registrado exitosamente", "Su servicio ya esta registrado puede encontrarlo en: "+ruta);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "save";
    }
	
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	@ResponseBody
    public String signupPost(@Valid @RequestBody String image) throws MessagingException {
		String extension=image.substring(11,14);
		if(image.charAt(13)=='e') {
			extension="jpeg";
		}
		String nombre="principal";
		logger.info(extension);
		byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((image.substring(image.indexOf(",")+1)).getBytes());
		s3Services.uploadFile(nombre,extension, bI);
		return "save";
	}
}
