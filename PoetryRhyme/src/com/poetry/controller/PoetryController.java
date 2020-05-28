package com.poetry.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.poetry.entity.Poem;
import com.poetry.entity.Poetry;
import com.poetry.entity.User;
import com.poetry.service.PoemServiceImpl;
import com.poetry.service.PoetryServiceImpl;

@Controller
@ResponseBody
@RequestMapping("/poetry")
public class PoetryController {
	@Resource
	private PoetryServiceImpl poetryServiceImpl;

	@RequestMapping("/get")
	public List<Poetry> back(Model model, HttpServletRequest request) throws IOException {
		List<Poetry> list = new ArrayList<>();
		list = this.poetryServiceImpl.getPoetry();
		System.out.println(list.size()+"首");
		for(Poetry p:list) {
			System.out.println(p.toString());
		}
		return list;

	}
	
	@RequestMapping("/search")
	public List<Poetry> search(Model model, HttpServletRequest request) throws IOException {
		
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		//字符流
		BufferedReader reader = new BufferedReader(isr);
		String jsonStr = reader.readLine();
		String str = new Gson().fromJson(jsonStr,String.class);
		System.out.println(str);
		List<Poetry> list = new ArrayList<>();
		
		list = this.poetryServiceImpl.findPoetry(str);
		
		System.out.println(list.size()+"首");
		for(Poetry p:list) {
			System.out.println(p.toString());
		}
		return list;
	}
	
	@RequestMapping("/weather")
	public Poetry weather(Model model, HttpServletRequest request) throws IOException {
		
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		//字符流
		BufferedReader reader = new BufferedReader(isr);
		String jsonStr = reader.readLine();
		String str = new Gson().fromJson(jsonStr,String.class);
		System.out.println(str);
		Poetry p = new Poetry();
		
		p = this.poetryServiceImpl.getPoetry(str);
		return p;
	}
	
}
