package com.poetry.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poetry.entity.Author;
import com.poetry.entity.Poetry;
import com.poetry.service.AuthorServiceImpl;
import com.poetry.service.PoetryServiceImpl;

@Controller
@ResponseBody
@RequestMapping("/author")
public class AuthorController {
	@Resource
	private AuthorServiceImpl authorServiceImpl;
	@RequestMapping("/get")
	public List<Author> back(Model model, HttpServletRequest request) throws IOException {
		List<Author> list = new ArrayList<>();
		list = this.authorServiceImpl.getAuthor();
		System.out.println(list.size()+"首");
		for(Author a:list) {
			System.out.println(a.toString());
		}
		return list;

	}
}
