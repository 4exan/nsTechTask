package ua.dev.techtask.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.dev.techtask.dto.BorrowDto;
import ua.dev.techtask.dto.BorrowedBookStat;
import ua.dev.techtask.entity.Book;
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

  @GetMapping("/borrowed/{memberName}")
  public ResponseEntity<List<Book>> getAllBorrowedBooksByMemberName(@PathVariable String memberName) {
    return ResponseEntity.ok(libraryService.getAllBorrowedBooksByMemberName(memberName));
  }

  @GetMapping("/borrowed/disctinct")
  public ResponseEntity<List<String>> getAllBorrowedBooks() {
    return ResponseEntity.ok(libraryService.findDistinctBorrowedBookTitles());
  }

  @GetMapping("/borrowed/statistics")
  public ResponseEntity<List<BorrowedBookStat>> getBorrowedBookStatistics() {
    return ResponseEntity.ok(libraryService.findBorrowedBookStats());
  }
}
