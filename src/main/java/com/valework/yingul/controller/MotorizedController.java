package com.valework.yingul.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.model.Yng_Security;
import com.valework.yingul.service.SecurityMotorized;

@RestController
@RequestMapping("/motorized")
public class MotorizedController {
		
	@Autowired
	private SecurityMotorized securityMotorized;
	
		@RequestMapping("/security")
    public List<Yng_Security> findSecurytiList() {
        List<Yng_Security> securityList = securityMotorized.findAll();
        return securityList;
    }
    

    
}
