package com.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOARD")
public class Test {

	@Id
	@Column(name="ID")
	private int id;
	@Column(name="TITLE")
	private String title;
	@Column(name="CONTENT")
	private String content;
	
	public Test(){}
	
	public Test(int id){
		super();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
