package com.poetry.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.PoemDaoImpl;
import com.poetry.entity.Poem;
import com.poetry.entity.User;

@Service
@Transactional(readOnly = false)
public class PoemServiceImpl {

	@Resource
	private PoemDaoImpl poemDaoImpl;

	public List<Poem> getPoem() {

		return this.poemDaoImpl.select();
	}
}
