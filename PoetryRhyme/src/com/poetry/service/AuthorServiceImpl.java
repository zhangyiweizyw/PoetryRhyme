package com.poetry.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.AuthorDaoImpl;
import com.poetry.dao.PoetryDaoImpl;
import com.poetry.entity.Author;
import com.poetry.entity.Poetry;

@Service
@Transactional(readOnly = false)
public class AuthorServiceImpl {
	@Resource
	private AuthorDaoImpl authorDaoImpl;

	public List<Author> getAuthor() {

		return this.authorDaoImpl.select();
	}
}
