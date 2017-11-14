package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.CategoryDao;
import com.valework.yingul.model.Yng_Category;
import com.valework.yingul.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
    private CategoryDao categoryDao;

	public List<Yng_Category> findAll() {
		return categoryDao.findAll();
	}

	public List<Yng_Category> findByItemTypeAndLevel(String itemType,int level) {
		return categoryDao.findByItemTypeAndLevel(itemType,level);
	}

	public List<Yng_Category> findByFatherId(Long father) {
		// TODO Auto-generated method stub
		return categoryDao.findByFatherId(father);
	}

	
}
