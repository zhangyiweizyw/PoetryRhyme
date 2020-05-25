package com.poetry.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.poetry.entity.User;
import com.poetry.service.UserServiceImpl;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserServiceImpl userServiceImpl;
	
	@RequestMapping("/register")
	public boolean register(Model model,HttpServletRequest request) throws IOException {
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		//字符流
		BufferedReader reader = new BufferedReader(isr);
		String jsonStr = reader.readLine();
		
		User user = new Gson().fromJson(jsonStr, User.class);
		System.out.println(user.toString());
		
		this.userServiceImpl.addUser(user);
		return true;
	}
	
	@RequestMapping("/back")
	public List<User> back(Model model,HttpServletRequest request) throws IOException {
		
		System.out.println("这是back");
		return this.userServiceImpl.back();
		
	}
	
	@RequestMapping("/load")
	public void load(Model model) {
		
		
		
	}
	
	
	

}
