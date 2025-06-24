package ua.dev.techtask.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "members")
@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @NotNull
  private String name;
  private LocalDateTime membershipDate;
  @OneToMany(mappedBy = "member")
  private List<Borrow> borrows;

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getMembershipDate() {
    return this.membershipDate;
  }

  public void setMembershipDate(LocalDateTime membershipDate) {
    this.membershipDate = membershipDate;
  }

  public List<Borrow> getBorrows() {
    return this.borrows;
  }

  public void setBorrows(List<Borrow> borrows) {
    this.borrows = borrows;
  }
}
