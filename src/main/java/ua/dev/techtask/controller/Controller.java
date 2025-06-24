package ua.dev.techtask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dev.techtask.dto.BookDto;
import ua.dev.techtask.entity.Book;
import ua.dev.techtask.service.BookService;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private BookService bookService;

    @GetMapping("/books/get-all")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/books")
    public void createNewBook(@RequestBody BookDto req){
        bookService.createBook(req);
    }

    @PostMapping("/books/edit")
    public void editBook(@RequestBody BookDto req){
        bookService.editBook(req);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }
    
}
