package com.poetry.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poetry.dao.CommunityTopicDaoImpl;
import com.poetry.entity.CommunityTopic;

@Service
@Transactional(readOnly=true)
public class CommunityTopicServiceImpl {
	
	@Resource
	private CommunityTopicDaoImpl communityTopicDaoImpl;
	
	
	//查询所有社区话题及其作者姓名
	public List<Object> findAllCommunityTopic(){
		List<Object> topics = new ArrayList<>();
		topics = this.communityTopicDaoImpl.selectCommunityTopic();
		return topics;
	}
	
	//根据ID查找单条社区话题
	public CommunityTopic findTopicById(int id) {
		CommunityTopic communityTopic = new CommunityTopic();
		communityTopic = this.communityTopicDaoImpl.selectCommunityTopicById(id);
		return communityTopic;
	}
}
