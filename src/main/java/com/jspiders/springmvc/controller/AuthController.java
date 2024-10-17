package com.jspiders.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestParam;

import com.jspiders.springmvc.dto.User;
import com.jspiders.springmvc.service.UserService;


@Controller
public class AuthController {

		@Autowired
		private UserService userService;

	@RequestMapping("/login")
		protected String getLogInPage() {
			return "login";
		}

		@RequestMapping("/signup")
		protected String getSignUpPage() {
			return "signup";
		}
		@RequestMapping("/edit")
		protected String getEditPage() {
			return "edit";
		}
		@RequestMapping(value = "/home", method = RequestMethod.GET)
		protected String getHomePage(HttpSession httpSession,ModelMap modelMap) {
			User user=(User) httpSession.getAttribute("user");
			if (user !=null) {
				modelMap.addAttribute("user", user);
				return "home";
			} else {
				return "login";
			}
		}
		
		@RequestMapping("/users")
		protected String addUser(@RequestParam(name = "username") String userName, @RequestParam(name = "email") String email,
				@RequestParam(name = "mobile") long mobile, @RequestParam(name = "password") String password,@RequestParam(name = "role") String role,ModelMap modelMap ) throws ClassNotFoundException {
			User addedUser=userService.addUser(userName, email, mobile, password,role);
			if (addedUser != null) {
				modelMap.addAttribute("message", "User added..");
				return "login";
			} else {
				modelMap.addAttribute("message", "Something went wrong..");
				return "signup";
			}
		}
		@RequestMapping(value = "/edit-user", method = RequestMethod.GET)
		protected String getEditPage(HttpSession httpSession, ModelMap modelMap) {
			User user = (User) httpSession.getAttribute("user");
			httpSession.setAttribute("user",user);
			if (user != null) {
				modelMap.addAttribute("user", user);
				return "edit";
			} else {
				return "login";
			}
		}
		@RequestMapping(value = "/update-user", method = RequestMethod.POST)
		protected String updateUser(@RequestParam(name = "id") int id, @RequestParam(name = "username") String userName,
				@RequestParam(name = "email") String email, @RequestParam(name = "mobile") long mobile,
				@RequestParam(name = "password") String password, ModelMap modelMap,HttpSession httpSession) {
			User updatedUser = userService.updateUser(id, userName, email, mobile, password);
			User user=(User) httpSession.getAttribute("user");
			httpSession.setAttribute("user", user);
			if (updatedUser != null) {
				modelMap.addAttribute("user", user);
				modelMap.addAttribute("message", "User updated..");
				
			} else {
				modelMap.addAttribute("message", "Something went wrong..");
			}
			return "home";
		}

		@RequestMapping(value = "/login", method = RequestMethod.POST)
		protected String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
				ModelMap modelMap,HttpSession httpSession) {
			User user1 = userService.login(email, password);
			if (user1 != null) {
				httpSession.setAttribute("user", user1);
				
			httpSession.setMaxInactiveInterval(30 * 24 * 60 * 60);
			modelMap.addAttribute("user", user1);
			if (user1.isBlocked()) {
				modelMap.addAttribute("message", "User is blocked..");
				return "login";
			} else {
				return "home";
			}

				
			} else {
				modelMap.addAttribute("message", "Invalid email or password..");
				return "login";
			}
		}
		@RequestMapping(value="/logout",method=RequestMethod.GET)
		protected String logout(HttpSession httpSession)
		{
            httpSession.invalidate();
			return "login";
			}
		@RequestMapping(value = "/delete-user", method = RequestMethod.GET)
		protected String deleteUser(HttpSession httpSession, ModelMap modelMap) {
			User user = (User) httpSession.getAttribute("user");
			if (user != null) {
				userService.deleteUser(user.getId());
				modelMap.addAttribute("message", "User deleted..");
			}
			httpSession.invalidate();
			return "login";
		}
		@RequestMapping(value = "/users", method = RequestMethod.GET)
		protected String findAllUsers(HttpSession httpSession, ModelMap modelMap) {
			User user = (User) httpSession.getAttribute("user");
			if (user != null) {
				List<User> users = userService.findAllUsers();
				modelMap.addAttribute("users", users);
				return "users";
			} else {
				return "login";
			}
		}

		@RequestMapping(value = "/block-user", method = RequestMethod.GET)
		protected String blockUser(@RequestParam(name = "id") int id, ModelMap modelMap, HttpSession httpSession) {
			User user = (User) httpSession.getAttribute("user");
			if (user != null) {
				User blockedUser = userService.blockUser(id);
				if (blockedUser != null) {
					List<User> users = userService.findAllUsers();
					modelMap.addAttribute("users", users);
				}
				return "users";
			} else {
				return "login";
			}
		}

		@RequestMapping(value = "/unblock-user", method = RequestMethod.GET)
		protected String unBlockUser(@RequestParam(name = "id") int id, ModelMap modelMap, HttpSession httpSession) {
			User user = (User) httpSession.getAttribute("user");
			if (user != null) {
				User blockedUser = userService.unBlockUser(id);
				if (blockedUser != null) {
					List<User> users = userService.findAllUsers();
					modelMap.addAttribute("users", users);
				}
				return "users";
			} else {
				return "login";
			}
		}
		
		

	}

