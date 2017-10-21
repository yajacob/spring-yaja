package net.inzoe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String writer;
	
	@Column(nullable = false)
	private String title;

	@Column(nullable = false, length=2000)
	private String contents;
	
	public Question() {}

	public Question(String writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	
}
