package com.jspiders.springmvc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.jspiders.springmvc.dto.User;
import com.jspiders.springmvc.dto.blogDTO;

@Component
public class UserDAO {
	private  EntityManagerFactory entityManagerFactory;
	private  EntityManager entityManager;
	private  EntityTransaction entityTransaction;
	
	

	public User addUser(User user){
		openConnection();
		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();
		closeConnection();
		return user;
	}
	
	public User login(String email, String password) {
		openConnection();
	 Query query = entityManager.createQuery("SELECT user from User user WHERE user.email = ?1 AND user.password = ?2");
		query.setParameter(1, email);
		query.setParameter(2, password);
		User user = (User) query.getSingleResult();
		closeConnection();
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers(){
		openConnection();
		Query query = entityManager.createQuery("SELECT user FROM User user");
		List<User> users = query.getResultList();
		closeConnection();
		return users;
	}
	
	


	private  void openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		entityManagerFactory = Persistence.createEntityManagerFactory("springmvc");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();	}

	private  void closeConnection() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
		if (entityManager != null) {
			entityManager.close();
		}
		if (entityTransaction != null) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}

	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		openConnection();
		User user = entityManager.find(User.class, id);
		entityTransaction.begin();
		entityManager.remove(user);
		entityTransaction.commit();
		closeConnection();
		
	}

	public  User updateUser(int id, String userName, String email, long mobile, String password) throws ClassNotFoundException {
		openConnection();
		User user = entityManager.find(User.class, id);
		user.setUserName(userName);
		user.setEmail(email);
		user.setMobile(mobile);
		user.setPassword(password);
		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();
		closeConnection();
		return user;
	}

	public  void mapBlogTOUser(int l, int m){
		openConnection();
		blogDTO dto=entityManager.find(blogDTO.class,l);
		User user=entityManager.find(User.class,m);
	//User user1=entityManager.find(User.class, user);
		
		List<blogDTO> blogs=user.getBlog();
		blogs.add(dto);
		user.setBlog(blogs);
		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();
		closeConnection();
		
	}
	public User blockUser(int id) {
		openConnection();
		User user = entityManager.find(User.class, id);
		user.setBlocked(true);
		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();
		closeConnection();
		return user;
	}

	public User unBlockUser(int id) {
		openConnection();
		User user = entityManager.find(User.class, id);
		user.setBlocked(false);
		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();
		closeConnection();
		return user;
	}
	
	

	
}
