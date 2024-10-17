package com.jspiders.springmvc.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jspiders.springmvc.dao.UserDAO;
import com.jspiders.springmvc.dao.blogDAO;
import com.jspiders.springmvc.dto.User;
import com.jspiders.springmvc.dto.blogDTO;

@Component
public class BlogService {

	@Autowired
	private blogDAO blogDAO1;
	@Autowired
	
	private UserDAO dao;

	public blogDTO addBlog(String title, String content, String auther, User user) {

		blogDTO blogdto = new blogDTO();
		blogdto.setTitle(title);
		blogdto.setContent(content);
		blogdto.setDate(new Date(System.currentTimeMillis()));
		blogdto.setAuthor(auther);
		blogdto.setUserId(user.getId());
		try {
			blogDTO dto2 = blogDAO1.addBlog(blogdto, user);

			dao.mapBlogTOUser(dto2.getId(), user.getId());
			return dto2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	public List<blogDTO> findMyBlogs(int userId) {
		List<blogDTO> blogs = blogDAO1.findMyBlogs(userId);
		if (blogs.size() > 0) {
			return blogs;
		} else {
			return null;
		}
	}

	public List<blogDTO> findAllWebBlogs() {
		List<blogDTO> blogDTOs = blogDAO1.findAllWebBlogs();
		if (blogDTOs.size() > 0) {
			return blogDTOs;
		} else {
			return null;
		}
	}

	public blogDTO update(int id, String title, String content, String date, String auther) {

		try {
			return blogDAO1.updateBlog(id, title, content, date, auther);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public blogDTO deleteBlog(int blogId, int userId) {
		
			return blogDAO1.deleteBlog(blogId, userId);
		
		
	}

	public blogDTO findBlogById(int id) {
	
		return blogDAO1.findBlogById(id);
	}
	public List<blogDTO> sortBlogs(int index) {
		List<blogDTO> blogs = blogDAO1.findAllWebBlogs();
		Collections.sort(blogs);
		if (index == 1) {
			Collections.reverse(blogs);
			return blogs;
		} else {
			return blogs;
		}
	}

	public List<blogDTO> searchBlogs(String query) {
		List<blogDTO> blogs = blogDAO1.searchBlogs(query);
		if (blogs.size() > 0) {
			return blogs;
		} else {
			return null;
		}
	}
}
