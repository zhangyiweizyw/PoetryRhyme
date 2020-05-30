package com.poetry.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poetry.entity.CommunityTopic;
import com.poetry.entity.OriginalPoetry;
import com.poetry.service.CommServiceImpl;
import com.poetry.service.OriginalPoetryServiceImpl;


@Controller
@ResponseBody
@RequestMapping("/oripoetry")
public class OriPoetryController {
	@Resource
	private OriginalPoetryServiceImpl opServiceImpl;

	
	@RequestMapping("/get")
	public String back(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		List<OriginalPoetry> list = new ArrayList<>();

		list = this.opServiceImpl.findAllOriginalPoetry();
		System.out.println(list.size()+"条");
		System.out.println(list.get(0).toString());
		/*String str = URLEncoder.encode(list.toString(), "utf-8");*/
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonStr = gson.toJson(list);
		String str = URLEncoder.encode(jsonStr, "utf-8");
		System.out.println(jsonStr);
		return str;

	}
	
	@RequestMapping("/add")
	public void add(Model model, HttpServletRequest request,HttpServletResponse response)throws IOException{
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		// 字符流
		BufferedReader reader = new BufferedReader(isr);
		String jsonStr = reader.readLine();
		OriginalPoetry op= new Gson().fromJson(jsonStr, OriginalPoetry.class);
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		this.opServiceImpl.addOriginalPoetry(op);
		
	}
	
	@RequestMapping("/update")
	public void update(Model model, HttpServletRequest request,HttpServletResponse response)throws IOException{
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		// 字符流
		BufferedReader reader = new BufferedReader(isr);
		String jsonStr = reader.readLine();
		OriginalPoetry op= new Gson().fromJson(jsonStr, OriginalPoetry.class);
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		this.opServiceImpl.updateOriginalPoetry(op);
	}

}
