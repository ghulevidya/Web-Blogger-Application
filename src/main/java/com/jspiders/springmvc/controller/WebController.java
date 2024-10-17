package com.jspiders.springmvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspiders.springmvc.dto.Role;
import com.jspiders.springmvc.dto.User;
import com.jspiders.springmvc.dto.blogDTO;
import com.jspiders.springmvc.service.BlogService;

@Controller
public class WebController {
	
	@Autowired
	private BlogService blogService;

	@RequestMapping(value="/add-blog-page", method=RequestMethod.GET)
	protected String add_blog(HttpSession httpSession)
	{
	User user=(User) httpSession.getAttribute("user");
	if(user !=null) {
		return "add_blog";}
	else {
		return "login";
	}
	
		
	}
	@RequestMapping(value="/add-blog",method=RequestMethod.POST)
	protected String addBlog(@RequestParam(name="title") String title,@RequestParam(name="content")String content,@RequestParam(name="auther")String auther,ModelMap modelMap, HttpSession httpSession){
		User user=(User)httpSession.getAttribute("user");
		blogDTO addedBlog=blogService.addBlog(title,content,auther,user);
		
		if(addedBlog !=null)
		{
			List<blogDTO> blogs = blogService.findMyBlogs(user.getId());
			
				modelMap.addAttribute("blogs", blogs);
				return "my_blogs";
		}
		else {
			modelMap.addAttribute("message","Something Wents wrong...");
		
			return "add_blog";}
		
		
	}
	@RequestMapping(value="/update-blog",method=RequestMethod.POST)
	protected String updateBlog(@RequestParam(name = "id") int id,@RequestParam(name="title") String title,@RequestParam(name="content") String content,@RequestParam(name="date")String date,@RequestParam(name="auther")String auther,ModelMap map,HttpSession httpSession) {
		blogDTO blogDTO=blogService.update(id,title,content,date,auther);
		User user=(User) httpSession.getAttribute("user");
		//httpSession.setAttribute("user", user);
		if(blogDTO !=null)
		{
			map.addAttribute("user",user);
		map.addAttribute("message", "Blog is Updated");
			
		}
		else {
			map.addAttribute("message","something wents wrong");
			
		}
		User user2=(User) httpSession.getAttribute("user");
		List<blogDTO> blogs=blogService.findMyBlogs(user.getId());
		map.addAttribute("blogs",blogs);
		return"my_blogs";
		
		
		
	}
	@RequestMapping(value="/my-blog",method=RequestMethod.GET)
	protected String myblogs(HttpSession httpSession,ModelMap modelMap) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			List<blogDTO> blogs = blogService.findMyBlogs(user.getId());
			if (blogs != null) {
				modelMap.addAttribute("blogs", blogs);
			} else {
				modelMap.addAttribute("message", "Blogs not found..");
			}
			return "my_blogs";
		} else {
			return "login";
		}
	}
	@RequestMapping(value = "/edit-blog", method = RequestMethod.GET)
	protected String getEditBlogPage(@RequestParam(name = "id") int id, HttpSession httpSession, ModelMap modelMap) {
		User user = (User) httpSession.getAttribute("user");
		httpSession.setAttribute("user", user);
		
		if (user != null) {
			modelMap.addAttribute("user",user);
			modelMap.addAttribute("blog", blogService.findBlogById(id));
			return "blogedit";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/delete-blog", method = RequestMethod.GET)
	protected String deleteBlog(@RequestParam(name = "id") int blogId,
			ModelMap modelMap, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			blogDTO deletedBlog = blogService.deleteBlog(blogId, user.getId());
			
			if (deletedBlog == null) {
				modelMap.addAttribute("message", "Something went wrong..");
			}
			List<blogDTO> blogs = null;
			if (user.getRole().equals(Role.USER)) {
				blogs = blogService.findMyBlogs(user.getId());
				if (blogs != null) {
					modelMap.addAttribute("blogs", blogs);
				} else {
					modelMap.addAttribute("message", "Blogs not found..");
				}
				return "my_blogs";
			} else {
				blogs = blogService.findAllWebBlogs();
				if (blogs != null) {
					modelMap.addAttribute("blogs", blogs);
				} else {
					modelMap.addAttribute("message", "Blogs not found..");
				}
				modelMap.addAttribute("role", user.getRole());
				return "blogs";
			}
		} else {
			return "login";
		}
		
		
	}
	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	protected String findAllBlogs(ModelMap modelMap, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			List<blogDTO> blogs = blogService.findAllWebBlogs();
			if (blogs != null) {
				modelMap.addAttribute("blogs", blogs);
			} else {
				modelMap.addAttribute("message", "Blogs not found..");
			}
			modelMap.addAttribute("role", user.getRole());
			return "blogs";
		} else {
			return "login";
		}
	}
	@RequestMapping(value = "/sort", method = RequestMethod.GET)
	protected String sortBlogs(@RequestParam(name = "index") int index, HttpSession httpSession, ModelMap modelMap) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			List<blogDTO> sortedBlogs = blogService.sortBlogs(index);
			modelMap.addAttribute("blogs", sortedBlogs);
			modelMap.addAttribute("role", user.getRole());
			return "blogs";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	protected String searchBlogs(@RequestParam(name = "query") String query, HttpSession httpSession,
			ModelMap modelMap) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			List<blogDTO> blogs = blogService.searchBlogs(query);
			if (blogs != null) {
				modelMap.addAttribute("blogs", blogs);
			} else {
				modelMap.addAttribute("message", "Blogs not found..");
			}
			modelMap.addAttribute("role", user.getRole());
			return "blogs";
		} else {
			return "login";
		}
	}



}
