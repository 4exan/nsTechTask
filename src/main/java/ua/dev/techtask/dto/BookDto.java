package ua.dev.techtask.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import ua.dev.techtask.entity.Book;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {

  private long id;
  private String title;
  private String author;
  private int amount;

  private List<Book> bookList;
  private Book book;

  public BookDto(String title, String author, int amount) {
    this.title = title;
    this.author = author;
    this.amount = amount;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getAmount() {
    return this.amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public List<Book> getBookList() {
    return this.bookList;
  }

  public void setBookList(List<Book> bookList) {
    this.bookList = bookList;
  }

  public Book getBook() {
    return this.book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

}
