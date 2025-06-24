package ua.dev.techtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowDto {

  public long memberId;
  public long bookId;

  public long getMemberId() {
    return this.memberId;
  }

  public void setMemberId(long memberId) {
    this.memberId = memberId;
  }

  public long getBookId() {
    return this.bookId;
  }

  public void setBookId(long bookId) {
    this.bookId = bookId;
  }
}
