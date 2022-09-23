package spring.web.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.web.library.entity.Author;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
	public Author findByName(String name);
}
