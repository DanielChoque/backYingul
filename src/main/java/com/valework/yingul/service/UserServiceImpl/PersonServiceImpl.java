package com.valework.yingul.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.PersonDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.PersonService;
@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Override
	public Yng_Person createPerson(Yng_Person person, Yng_User user) {
		Yng_Person localPerson=person;
		localPerson.setYng_User(userDao.findByEmail(user.getEmail()));
		personDao.save(localPerson);
		return localPerson;
		
	}
	
}
