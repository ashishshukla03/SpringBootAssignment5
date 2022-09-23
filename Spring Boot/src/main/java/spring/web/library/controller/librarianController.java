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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.web.library.entity.Librarian;
import spring.web.library.repository.LibrarianRepository;



@RestController
@RequestMapping("/librarians")
public class librarianController {
	

	private final LibrarianRepository librarianRepository;
	
	public librarianController(LibrarianRepository librarianRepository) {
		super();
		this.librarianRepository = librarianRepository;
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<Librarian> getLibrarian(@PathVariable("id")int id) {
		Librarian librarian= librarianRepository.getById(id);
		if (librarian == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(librarian));
	}
	
	@GetMapping(path="/email-id/{id}")
	public ResponseEntity<Librarian> getLibrarianByUsername(@PathVariable("id")String email) {
		Librarian librarian= librarianRepository.findByEmailIgnoreCase(email);
		if (librarian == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(librarian));
	}
	
	@GetMapping()
	public ResponseEntity<Librarian> getLibrarianByUsernameAndPassword(@RequestParam("username")String email, @RequestParam("password")String password){
		Librarian librarian = librarianRepository.findByEmailAndPassword(email, password);
		if (librarian == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.of(Optional.of(librarian));
		}
	}
	
	@GetMapping(path="/all-librarians")
	public ResponseEntity<List<Librarian>> getLibrarians(){
		List<Librarian> librarianList= librarianRepository.findAll();
		if (librarianList.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(librarianList));
	}
	
	
	
	@PostMapping(consumes= {"application/json", "application/xml"})
	public ResponseEntity<Librarian> postLibrarian(@RequestBody Librarian librarian) {
		Librarian createdLibrarian = null;
		try {
			createdLibrarian = librarianRepository.save(librarian);
			return ResponseEntity.of(Optional.of(createdLibrarian));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping()
	public ResponseEntity<Librarian> putLibrarian(@RequestBody Librarian librarian) {
		Librarian updatedLibrarian = null;
		try {
			updatedLibrarian = librarianRepository.save(librarian);
			return ResponseEntity.of(Optional.of(updatedLibrarian));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteLibrarianById(@PathVariable("id")int id) {
		try {
			librarianRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}catch(NoSuchElementException nse) {
			nse.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<Void> deleteLibrarian(@RequestBody Librarian librarian) {
		try {
			librarianRepository.delete(librarian);
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
