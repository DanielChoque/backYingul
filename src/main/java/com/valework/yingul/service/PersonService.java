package com.valework.yingul.service;

import com.valework.yingul.model.Yng_Person;
import com.valework.yingul.model.Yng_User;


public interface PersonService {
	Yng_Person createPerson(Yng_Person person, Yng_User user);
}
