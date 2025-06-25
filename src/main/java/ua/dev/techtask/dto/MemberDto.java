package ua.dev.techtask.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import ua.dev.techtask.entity.Member;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

  private long id;
  private String name;
  private LocalDateTime membershipDate;

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
}
