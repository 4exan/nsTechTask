package ua.dev.techtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowDto {

  private Long memberId;
  private Long bookId;

  public Long getMemberId() {
    return this.memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public Long getBookId() {
    return this.bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }
}
