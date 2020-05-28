package com.poetry.controller;

import java.io.IOException;
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
import com.poetry.service.CommServiceImpl;


@Controller
@ResponseBody
@RequestMapping("/comm")
public class CommController {
	@Resource
	private CommServiceImpl commServiceImpl;

	
	@RequestMapping("/get")
	public String back(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		List<CommunityTopic> list = new ArrayList<>();

		list = this.commServiceImpl.findAllCommunityTopic();
		System.out.println(list.size()+"条");
		System.out.println(list.get(0).toString());
		/*String str = URLEncoder.encode(list.toString(), "utf-8");*/
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonStr = gson.toJson(list);
		String str = URLEncoder.encode(jsonStr, "utf-8");
		System.out.println(jsonStr);
		return str;

	}

}
