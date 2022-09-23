package spring.web.library.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@Entity
@Table(name="book")
@JacksonXmlRootElement(localName="book")
public class Book {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="added_date")
	@JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
	private LocalDate addedDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="author_id",referencedColumnName="id")
	private Author author;

	public Book() {
		super();
	}

	public Book(int id, String title, LocalDate addedDate, Author author) {
		super();
		this.id = id;
		this.title = title;
		this.addedDate = addedDate;
		this.author = author;
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

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
}
