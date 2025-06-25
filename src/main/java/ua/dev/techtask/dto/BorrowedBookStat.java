package ua.dev.techtask.dto;

public class BorrowedBookStat {
  String title;
  long count;

  public BorrowedBookStat(String title, long count) {
    this.title = title;
    this.count = count;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getCount() {
    return this.count;
  }

  public void setCount(long count) {
    this.count = count;
  }
}
