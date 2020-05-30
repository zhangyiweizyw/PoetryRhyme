package com.poetry.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.CommDaoImpl;
import com.poetry.entity.CommunityTopic;

@Service
@Transactional(readOnly=false)
public class CommServiceImpl {
	
	@Resource
	private CommDaoImpl commDaoImpl;
	
	
	//查询所有社区话题及其作者姓名
	public List findAllCommunityTopic(){
		List topics = new ArrayList<>();
		topics = this.commDaoImpl.selectCommunityTopic();
		return topics;
	}
	
	//根据ID查找单条社区话题
	public CommunityTopic findTopicById(int id) {
		CommunityTopic communityTopic = new CommunityTopic();
		communityTopic = this.commDaoImpl.selectCommunityTopicById(id);
		return communityTopic;
	}
	
	//新增一条社区话题
	public void addCommunityTopic(CommunityTopic ct){
		this.commDaoImpl.addCommunityTopic(ct);
	}
	//修改信息
	public void updateCommunityTopic(CommunityTopic ct){
		this.commDaoImpl.updateCommunityTopic(ct);
	}
}
