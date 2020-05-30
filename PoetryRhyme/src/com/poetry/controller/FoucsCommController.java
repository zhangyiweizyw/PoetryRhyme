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

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poetry.dao.OrigPoetryDaoImpl;
import com.poetry.entity.CommunityTopic;
import com.poetry.entity.OriginalPoetry;
import com.poetry.entity.User;
import com.poetry.service.CommServiceImpl;
import com.poetry.service.OriginalPoetryServiceImpl;
import com.poetry.service.UserServiceImpl;

@Controller
@ResponseBody
@RequestMapping("/foucscomm")
public class FoucsCommController {

	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private CommServiceImpl commServiceImpl;
	@Resource
	private OriginalPoetryServiceImpl opServiceImpl;


	@RequestMapping("/get")
	public String back(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		// 字符流
		BufferedReader reader = new BufferedReader(isr);
		String jsonStr = reader.readLine();
		int myuserid = new Gson().fromJson(jsonStr, Integer.class);
		
		System.out.println(myuserid + "id");

		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		//获取所有原创诗词和社区话题
		List<CommunityTopic> list = this.commServiceImpl.findAllCommunityTopic();
		List<OriginalPoetry> list2 = this.opServiceImpl.findAllOriginalPoetry();
		
		List<CommunityTopic> comms=new ArrayList<>();
		List<OriginalPoetry> oris=new ArrayList<>();
		//获得用户关注的所有用户
		List<User> users = userServiceImpl.findFoucsUser(myuserid);
		
		for(int i=0;i<users.size();i++){
			for(int j=0;j<list.size();j++){
				if(list.get(j).getUser().getId()==users.get(i).getId()){
					comms.add(list.get(j));
				}
			}
			for(int j=0;j<list2.size();j++){
				if(list2.get(j).getUser().getId()==users.get(i).getId()){
					oris.add(list2.get(j));
				}
			}
		}
		
		/*for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getCommunityTopics().size() != 0) {
				for (CommunityTopic ct : users.get(i).getCommunityTopics()) {
					list.add(ct);
				}
			}
			if (users.get(i).getOrigianlPoetrys().size() != 0) {
				for (OriginalPoetry op : users.get(i).getOrigianlPoetrys()) {
					list2.add(op);
				}
			}
		}*/
		//转换类型
		for(int i=0;i<list2.size();i++){
			CommunityTopic ct=new CommunityTopic();
			ct.setId(list2.get(i).getId());
			ct.setTitle(list2.get(i).getTitle());
			ct.setType(list2.get(i).getType());
			ct.setUser(list2.get(i).getUser());
			ct.setContent(list2.get(i).getContent());
			ct.setIssuedate(list2.get(i).getIssuedate());
			ct.setLikequantity(list2.get(i).getLikequantity());
			ct.setPageview(list2.get(i).getPageview());
			ct.setCommentquantity(list2.get(i).getCommentquantity());
			list.add(ct);
			
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonStr1 = gson.toJson(list);
		String str1 = URLEncoder.encode(jsonStr1, "utf-8");
		return str1;

	}

}
