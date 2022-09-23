package spring.web.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.web.library.entity.Book;

public interface BookRepository extends JpaRepository<Book,Integer> {

}
