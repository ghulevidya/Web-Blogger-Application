package com.jspiders.springmvc.service;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.jspiders.springmvc.dao.UserDAO;
import com.jspiders.springmvc.dto.Role;
import com.jspiders.springmvc.dto.User;
import com.jspiders.springmvc.dto.blogDTO;

@Component
public class UserService {
	
	@Autowired
	private UserDAO userDAO;

	public User addUser(String userName, String email, long mobile, String password, String role) throws ClassNotFoundException {
		
		if(role.equals("ADMIN")){
		boolean flag=false;
		List<User> list=userDAO.findAllUsers();
		for (User user : list) {
			if(user.getRole().equals(Role.ADMIN)) {
				flag=true;
				break;
			}
		}
		if(flag) {
			return null;
		}
		}
		User user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		user.setMobile(mobile);
		user.setPassword(password);
		if(role.equals("USER")) {
			user.setRole(Role.USER);
		}
		else {
			user.setRole(Role.ADMIN);
		}
		user.setBlog(new ArrayList<blogDTO>());
		try {
			return userDAO.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public User login(String email, String password) {
		try {
			return userDAO.login(email, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public void deleteUser(int id) {
		userDAO.deleteUser(id);
		
	}
	public User updateUser(int id, String userName, String email, long mobile, String password) {
		try {
			return userDAO.updateUser(id, userName, email, mobile, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	
	}
	public List<User> findAllUsers() {
		try {
			return userDAO.findAllUsers();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public User blockUser(int id) {
		try {
			return userDAO.blockUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User unBlockUser(int id) {
		try {
			return userDAO.unBlockUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
