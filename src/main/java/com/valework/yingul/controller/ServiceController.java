package com.valework.yingul.controller;

import java.util.Set;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ItemCategory;
import com.valework.yingul.model.Yng_Service;

import com.valework.yingul.service.UserService;

@Controller
public class ServiceController {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@RequestMapping(value = "/service", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public String servicePost(@RequestBody Yng_Service service) throws JSONException {
		Yng_Item item = service.getYng_Item();
		Set<Yng_ItemCategory> itemCategory = item.getItemCategory();
		for (Yng_ItemCategory s : itemCategory) {
		    LOG.info(s.getCategory().getName().toString());
		}
        return item.getItemCategory().toString();
    }
	
}
