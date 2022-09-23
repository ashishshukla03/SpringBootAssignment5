package spring.web.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.web.library.entity.Librarian;

public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {

	public Librarian findByEmailIgnoreCase(String email);
	
	public Librarian findByEmailAndPassword(String email, String password);
}
