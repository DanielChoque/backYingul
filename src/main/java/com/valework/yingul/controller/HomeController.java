package com.valework.yingul.controller;

import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valework.yingul.dao.CityDao;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.RoleDao;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.security.Yng_UserRole;
import com.valework.yingul.service.UserService;

@Controller
public class HomeController {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private ProvinceDao provinceDao;
	
	@Autowired
    private RoleDao roleDao;
	
	/*@RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        Yng_User user = new Yng_User();

        model.addAttribute("user", user);

        return "signup";
    }*/
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
    public String signupPost(@Valid @RequestBody Yng_User user) throws MessagingException {
        if (userService.checkEmailExists(user.getEmail())) {
            return "email exist";
        } else {
        	//para la provincia y ciudad 
        	user.setYng_Province(provinceDao.findByName(user.getYng_Province().getName()));
        	user.setYng_City(cityDao.findByName(user.getYng_City().getName()));
        	//LOG.info("eddy"+user.getYng_Province().getName().toString());
        	//para los roles
        	Set<Yng_UserRole> userRoles = new HashSet<>();
            userRoles.add(new Yng_UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            return "save";
        }
    }
	
}
