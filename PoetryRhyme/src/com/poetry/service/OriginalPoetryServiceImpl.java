package com.poetry.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.OrigPoetryDaoImpl;
import com.poetry.entity.OriginalPoetry;

@Service
@Transactional(readOnly = false)
public class OriginalPoetryServiceImpl {
	
	@Resource
	private OrigPoetryDaoImpl originalPoetryDaoImpl;
	
	//查询所有原创诗词及其作者姓名
	public List<OriginalPoetry> findAllOriginalPoetry(){
		List<OriginalPoetry> poetrys = new ArrayList<>();
		poetrys = this.originalPoetryDaoImpl.selectOriginalPeotry();
		return poetrys;
	}
	
	//根据ID查找单条原创诗词
	public OriginalPoetry findPoetryById(int id) {
		OriginalPoetry originalPoetry = new OriginalPoetry();
		originalPoetry = this.originalPoetryDaoImpl.selectOriginalPoetryById(id);
		return originalPoetry;
	}
	
	//新增一条原创诗词
	public void addOriginalPoetry(OriginalPoetry op){
		this.originalPoetryDaoImpl.addOriginalPoetry(op);
	}
	//修改信息
	public void updateOriginalPoetry(OriginalPoetry op){
		this.originalPoetryDaoImpl.updateOriginalPoetry(op);
	}
	
	
}
