package spring.web.library.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

import spring.web.library.entity.Book;
import spring.web.library.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class bookController {



	private final BookRepository bookRepository;
	
	
	public bookController(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}
	
	
	@GetMapping()
	public ResponseEntity<List<Book>> getBooks(){
		List<Book> bookList = bookRepository.findAll();
		if (bookList.size()<=0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(bookList));
		
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id")int id) {
		Book book =  bookRepository.getById(id);
		if (book == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(book));
	}
	
	
	@PostMapping()
	public ResponseEntity<Book> postBook(@RequestBody Book book){
		Book createdBook = null;
		try {
			createdBook = bookRepository.save(book);
			return ResponseEntity.of(Optional.of(createdBook));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping()
	public ResponseEntity<Book> putBook(@RequestBody Book book) {
		Book updatedBook = null;
		try {
			updatedBook=bookRepository.save(book);
			return ResponseEntity.of(Optional.of(updatedBook));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteBookById(@PathVariable("id")int id) {
		try {
			bookRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(NoSuchElementException nse) {
			nse.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
