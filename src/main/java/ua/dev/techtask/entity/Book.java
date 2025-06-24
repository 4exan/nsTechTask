package ua.dev.techtask.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Table(name = "books")
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotNull(message = "Title is required")
  @Size(min = 3, message = "Must be at least 3 characters long")
  @Pattern(regexp = "^[A-Z].*", message = "Must start with capital letter")
  private String title;
  @NotNull(message = "Author is required")
  @Pattern(regexp = "^[A-Z][a-z]+\\s[A-Z][a-z]+$", message = "Must contain two words, each starting with a capital letter")
  private String author;
  private int amount;
  @OneToMany(mappedBy = "book")
  private List<Borrow> borrows;

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

  public List<Borrow> getBorrows() {
    return this.borrows;
  }

  public void setBorrows(List<Borrow> borrows) {
    this.borrows = borrows;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || o.getClass() != getClass()) {
      return false;
    }
    Book book = (Book) o;
    return book.getTitle() == this.getTitle() && book.getAuthor() == this.getAuthor();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.title, this.author);
  }
}
