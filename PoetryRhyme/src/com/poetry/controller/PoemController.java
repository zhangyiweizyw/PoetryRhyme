package com.poetry.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poetry.entity.Poem;
import com.poetry.entity.User;
import com.poetry.service.PoemServiceImpl;
import com.poetry.service.UserServiceImpl;

@Controller
@ResponseBody
@RequestMapping("/poem")
public class PoemController {

	@Resource
	private PoemServiceImpl poemServiceImpl;

	@RequestMapping("/getPoems")
	public List<Poem> back(Model model, HttpServletRequest request) throws IOException {

		return this.poemServiceImpl.getPoem();

	}

}
