package com.poetry.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.PoemDaoImpl;
import com.poetry.dao.PoetryDaoImpl;
import com.poetry.entity.Poem;
import com.poetry.entity.Poetry;

@Service
@Transactional(readOnly = false)
public class PoetryServiceImpl {
	@Resource
	private PoetryDaoImpl poetryDaoImpl;

	public List<Poetry> getPoetry() {

		return this.poetryDaoImpl.select();
	}
	
	public List<Poetry> findPoetry(String str){
		return this.poetryDaoImpl.find(str);
	}
}
