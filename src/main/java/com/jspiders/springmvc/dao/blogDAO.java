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
public class blogDAO {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	public blogDTO addBlog(blogDTO dto, User user) {
		openConnection();
		entityTransaction.begin();
		entityManager.persist(dto);
		entityTransaction.commit();
		closeConnection();
		return dto;
	}

	public List<blogDTO> findAllWebBlogs() {
		openConnection();
		Query query = entityManager.createQuery("SELECT blog FROM blogDTO blog");
		@SuppressWarnings("unchecked")
		List<blogDTO> blogDTOs = query.getResultList();
		closeConnection();
		return blogDTOs;
	}

	public blogDTO updateBlog(int id, String title, String content, String date, String auther) {
		openConnection();
		blogDTO blogDTO1 = entityManager.find(blogDTO.class, id);
		blogDTO1.setTitle(title);
		blogDTO1.setContent(content);
		blogDTO1.setAuthor(auther);
		entityTransaction.begin();
		entityManager.persist(blogDTO1);
		entityTransaction.commit();
		closeConnection();
		return blogDTO1;
	}

	private void openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		entityManagerFactory = Persistence.createEntityManagerFactory("springmvc");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	private void closeConnection() {
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

	public blogDTO deleteBlog(int blogId, int userId) {

		openConnection();
		blogDTO blog = entityManager.find(blogDTO.class, blogId);
		User user = entityManager.find(User.class, userId);
		List<blogDTO> blogs = user.getBlog();
		blogDTO blogToBeDeleted = null;
		for (blogDTO b : blogs) {
			if (b.getId() == blogId) {
				blogToBeDeleted = b;
				break;
			}
		}
		blogs.remove(blogToBeDeleted);
		user.setBlog(blogs);
		entityTransaction.begin();
		entityManager.persist(user);
		entityManager.remove(blog);
		entityTransaction.commit();
		closeConnection();
		return blog;

	}

	public List<blogDTO> findMyBlogs(int userId) {
		openConnection();
		User user = entityManager.find(User.class, userId);
		List<blogDTO> blogs = user.getBlog();
		closeConnection();
		return blogs;	
	
	}

	public  blogDTO findBlogById(int id) {
		openConnection();
		blogDTO blog=entityManager.find(blogDTO.class, id);
		closeConnection();
		
		return blog;
	}
	@SuppressWarnings("unchecked")
	public List<blogDTO> searchBlogs(String query) {
		openConnection();
		Query query2 = entityManager.createQuery("SELECT blogs FROM blogDTO blogs WHERE blogs.title LIKE '%" + query
				+ "%' OR blogs.content LIKE '%" + query + "%' OR blogs.author LIKE '%" + query + "%'");
		List<blogDTO> blogs = query2.getResultList();
		closeConnection();
		return blogs;
	}

}
