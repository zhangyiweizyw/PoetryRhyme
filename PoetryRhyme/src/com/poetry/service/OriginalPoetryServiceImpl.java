package com.poetry.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.OriginalPoetryDaoImpl;
import com.poetry.entity.OriginalPoetry;

@Service
@Transactional(readOnly = true)
public class OriginalPoetryServiceImpl {
	
	@Resource
	private OriginalPoetryDaoImpl originalPoetryDaoImpl;
	
	//查询所有原创诗词及其作者姓名
	public List<Object> findAllOriginalPoetry(){
		List<Object> poetrys = new ArrayList<>();
		poetrys = this.originalPoetryDaoImpl.selectOriginalPeotry();
		return poetrys;
	}
	
	//根据ID查找单条原创诗词
	public OriginalPoetry findPoetryById(int id) {
		OriginalPoetry originalPoetry = new OriginalPoetry();
		originalPoetry = this.originalPoetryDaoImpl.selectOriginalPoetryById(id);
		return originalPoetry;
	}
	
	
}
