package com.valework.yingul.service.UserServiceImpl;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valework.yingul.dao.ProvinceDao;
import com.valework.yingul.dao.ServiceDao;
import com.valework.yingul.model.Yng_Service;
import com.valework.yingul.model.Yng_ServiceProvince;
import com.valework.yingul.service.ServiceService;
import com.valework.yingul.service.UserService;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService{
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private ServiceDao serviceDao;
	
	@Autowired
	private ProvinceDao provinceDao;
	
	public Yng_Service findByServiceId(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Yng_Service createService(Yng_Service service, Set<Yng_ServiceProvince> cobertureZone) {
		Yng_Service localService = serviceDao.findByServiceId(service.getServiceId());

        if (localService != null) {
            LOG.info("User with service {} already exist. Nothing will be done. ");
        } else {
            for (Yng_ServiceProvince ur : cobertureZone) {
                provinceDao.save(ur.getProvince());
            }
            service.getCobertureZone().addAll(cobertureZone);
            
            localService = serviceDao.save(service);
        }

        return localService;
	}

}
