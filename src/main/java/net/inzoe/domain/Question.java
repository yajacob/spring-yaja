package net.inzoe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Question extends AbstractEntity {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	@JsonProperty
	private User writer;

	@Column(nullable = false)
	@JsonProperty
	private String title;

	@JsonProperty
	private Integer countOfAnswer = 0;

	@Column(nullable = false, length = 4000)
	@JsonProperty
	private String contents;

	@OneToMany(mappedBy = "question")
	@OrderBy("id ASC")
	private List<Answer> answers;

	public Question() {
	}

	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public int getAnswerCount() {
		if (this.answers == null)
			return 0;
		return this.answers.size();
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}

	public void addAnswer() {
		this.countOfAnswer += 1;
	}

	public void deleteAnswer() {
		this.countOfAnswer -= 1;
	}

	@Override
	public String toString() {
		return "Question [" + super.toString() + "writer=" + writer + ", title=" + title + ", countOfAnswer="
				+ countOfAnswer + ", contents=" + contents + ", answers=" + answers + "]";
	}

}
