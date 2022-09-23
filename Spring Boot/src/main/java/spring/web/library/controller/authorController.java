package spring.web.library.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.web.library.entity.Author;
import spring.web.library.repository.AuthorRepository;

@RestController
@RequestMapping("/authors")
public class authorController {

	private final AuthorRepository authorRepository;
	
	@Autowired
	public authorController(AuthorRepository authorRepository) {
		this.authorRepository=authorRepository;
	}
	
	@GetMapping(path="/{id}", produces= {"application/json"})
	public ResponseEntity<Author> getAuthorById(@PathVariable("id")String id) {
		Author author = authorRepository.getById(Integer.parseInt(id));
		if (author == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(author));
	}
	
	@GetMapping(path="/name/{name}", produces= {"application/json"})
	public ResponseEntity<Author> getAuthorByName(@PathVariable("name")String name) {
		Author author = authorRepository.findByName(name);
		if (author == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(author));
	}
	
	@GetMapping()
	public ResponseEntity<List<Author>> getAuthors(){
		List<Author> authorList = authorRepository.findAll();
		if (authorList.size() <=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(authorList));
		
	}
	
	@PostMapping()
	public ResponseEntity<Author> postAuthor(@RequestBody Author author) {
		Author createdAuthor = null;
		try {
			createdAuthor=authorRepository.save(author);
			return ResponseEntity.of(Optional.of(createdAuthor));
		}catch(Exception e) {
			e.printStackTrace();
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping()
	public ResponseEntity<Author> putAuthor(@RequestBody Author author) {
		Author updatedAuthor = null;
		try {
			updatedAuthor= authorRepository.save(author);
			return ResponseEntity.of(Optional.of(updatedAuthor));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteAuthorById(@PathVariable("id")int id) {
		try {
			authorRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(NoSuchElementException nse) {
			nse.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<Void> deleteAuthor(@RequestBody Author author) {
		try {
			authorRepository.delete(author);
			return ResponseEntity.ok().build();
		}catch(NoSuchElementException nse) {
			nse.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
