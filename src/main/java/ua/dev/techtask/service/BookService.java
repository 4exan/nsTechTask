package ua.dev.techtask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.dev.techtask.dto.BookDto;
import ua.dev.techtask.entity.Book;
import ua.dev.techtask.repository.BookRepository;

@Service
public class BookService {
    
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    
    //TODO impl search logic
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    //TODO impl creation logic
    public void createBook(BookDto req){
        Book book = new Book();
        book.setTitle(req.getTitle());
        book.setAuthor(req.getAuthor());
        saveBookToDB(book);
    }

    public void editBook(BookDto req){
        Book book = bookRepository.findById(req.getId()).orElseThrow(() -> new RuntimeException("No such Book"));
        book.setTitle(req.getTitle());
        book.setAuthor(req.getAuthor());
        book.setAmount(req.getAmount());
        saveBookToDB(book);
    }

    //TODO impl deletion logic
    public void deleteBook(BookDto req){
        removeBookFromDB(req.getId());
    }

    private void saveBookToDB(Book book){
        try{
            bookRepository.save(book);
        }catch(Exception e){
            throw new RuntimeException("Error occured while saving book to DB ->", e);
        }
    }

    private void removeBookFromDB(long id){
        try{
            bookRepository.deleteById(id);
        }catch(Exception e){
            throw new RuntimeException("Error occured while deleting book from DB ->", e);
        }
    }
}
