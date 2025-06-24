package ua.dev.techtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dev.techtask.dto.BorrowDto;
import ua.dev.techtask.service.LibraryService;

@RestController
@RequestMapping("/api/lib")
public class LibraryController {

  @Autowired
  private LibraryService libraryService;

  @PostMapping("/borrow")
  public void borrowBook(@RequestBody BorrowDto req) {
    libraryService.borrowBook(req);
  }

  @PostMapping("/return")
  public void returnBook(@RequestBody BorrowDto req) {
    libraryService.returnBook(req);
  }
}
