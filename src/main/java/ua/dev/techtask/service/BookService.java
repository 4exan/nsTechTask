package ua.dev.techtask.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.dev.techtask.dto.BookDto;
import ua.dev.techtask.entity.Book;
import ua.dev.techtask.repository.BookRepository;
import ua.dev.techtask.repository.BorrowRepository;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private BorrowRepository borrowRepository;

  // TODO impl search logic
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public void createBook(BookDto req) {
    Book book;
    Optional<Book> existing = bookRepository.findByTitleAndAuthor(req.getTitle(), req.getAuthor());
    if (existing.isPresent()) {
      book = existing.get();
      book.setAmount(book.getAmount() + 1);
    } else {
      book = new Book();
      book.setTitle(req.getTitle());
      book.setAuthor(req.getAuthor());
      book.setAmount(1);
    }
    saveBookToDB(book);
  }

  public void editBook(BookDto req) {
    Book book = bookRepository.findById(req.getId()).orElseThrow(() -> new RuntimeException("No such Book"));
    book.setTitle(req.getTitle());
    book.setAuthor(req.getAuthor());
    book.setAmount(req.getAmount());
    saveBookToDB(book);
  }

  // TODO test
  public void deleteBook(long id) {
    int borrowed = borrowRepository.countByBookIdAndReturnDateIsNull(id);
    if (borrowed >= 1) {
      throw new IllegalStateException("Cannot delete book with active borrows");
    } else {
      bookRepository.deleteById(id);
    }
  }

  private void saveBookToDB(Book book) {
    try {
      bookRepository.save(book);
    } catch (Exception e) {
      throw new RuntimeException("Error occured while saving book to DB ->", e);
    }
  }

  private Book getBookById(long id) {
    return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("No such Book in DB"));
  }

  private void removeBookFromDB(long id) {
    try {
      bookRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Error occured while deleting book from DB ->", e);
    }
  }
}
