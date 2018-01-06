package com.valework.yingul.controller;

import java.security.Principal;
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

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.RoleDao;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.model.security.Yng_UserRole;
import com.valework.yingul.service.PersonService;
import com.valework.yingul.service.UserService;

@Controller
public class HomeController {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userService;
		
	@Autowired 
	private PersonService personService;
	
	@Autowired
    private RoleDao roleDao;
	
	@Autowired
	private SmtpMailSender smtpMailSender;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
    public String signupPost(@Valid @RequestBody Yng_Person person) throws MessagingException {
		Yng_User user=person.getYng_User();
		user.setYng_Ubication(null);
		String password= user.getPassword();
		user.setUsername(person.getName()+person.getLastname());
		LOG.info(user.getUsername());
		if(userService.checkUsernameExists(user.getUsername())) {
			LOG.info("existe"+user.getUsername());
			int aux=0;
			while(userService.checkUsernameExists(user.getUsername())){
				aux++;
				user.setUsername(user.getUsername()+aux);
				LOG.info(user.getUsername());
			}
		}
		LOG.info(user.getUsername());
		if (userService.checkEmailExists(user.getEmail())) {
            return "email exist";
        } else {     	
        	Set<Yng_UserRole> userRoles = new HashSet<>();
            userRoles.add(new Yng_UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.createUser(user, userRoles);
            Yng_User temp = userService.findByEmail(user.getEmail());
            personService.createPerson(person, temp);
            smtpMailSender.send(user.getEmail(), "Autenticado exitosamente", "Ya esta autenticado su password es:"+password);
            return "save";
        }
    }
	
	@RequestMapping("/userFront")
	@ResponseBody
	public String userFront(Principal principal, Model model) {
        return principal.getName().toString();
    }
	
}
