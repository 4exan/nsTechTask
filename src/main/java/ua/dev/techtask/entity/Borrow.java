package ua.dev.techtask.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "borrows")
@Entity
public class Borrow {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @ManyToOne
  @JoinColumn(name = "member_id")
  @JsonIgnore
  private Member member;
  @ManyToOne
  @JoinColumn(name = "book_id")
  @JsonIgnore
  private Book book;
  private LocalDateTime borrowDate;
  private LocalDateTime returnDate;

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Member getMember() {
    return this.member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Book getBook() {
    return this.book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public LocalDateTime getBorrowDate() {
    return this.borrowDate;
  }

  public void setBorrowDate(LocalDateTime borrowDate) {
    this.borrowDate = borrowDate;
  }

  public LocalDateTime getReturnDate() {
    return this.returnDate;
  }

  public void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }
}
