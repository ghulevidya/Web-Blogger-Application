package com.jspiders.springmvc.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="blogs")
public class blogDTO implements Comparable<blogDTO> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false,unique=false)
	private String title;
	@Column(nullable=false,unique=false)
	private String content;
	@Column(nullable=false,unique=false)
	private Date date;
	@Column(nullable=false,unique=false)
	private String author;
	@Column(nullable = false, unique = false)
	private int userId;
	@Override
	public int compareTo(blogDTO o) {
		if(this.date.after(o.date)) {
			return 1;
		}
		else if(this.date.before(o.date)) {
			return -1;
		}
		else {
			return 0;
		}
		
	}
	
	

}
