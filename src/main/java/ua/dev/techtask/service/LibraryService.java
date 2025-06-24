package ua.dev.techtask.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ua.dev.techtask.dto.BorrowDto;
import ua.dev.techtask.entity.Book;
import ua.dev.techtask.entity.Borrow;
import ua.dev.techtask.entity.Member;
import ua.dev.techtask.exception.NoSuchBookException;
import ua.dev.techtask.exception.NoSuchBorrowException;
import ua.dev.techtask.exception.NoSuchMemberException;
import ua.dev.techtask.repository.BookRepository;
import ua.dev.techtask.repository.BorrowRepository;
import ua.dev.techtask.repository.MemberRepository;

@Service
public class LibraryService {

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private BorrowRepository borrowRepository;
  @Value("${borrow.limit}")
  private int borrowLimit;

  @Transactional
  public void borrowBook(BorrowDto req) {
    Book book = bookRepository.findById(req.getBookId()).orElseThrow(() -> new NoSuchBookException());
    if (book.getAmount() <= 0) {
      throw new IllegalStateException("No available book to borrow");
    }
    Member member = memberRepository.findById(req.getMemberId()).orElseThrow(() -> new NoSuchMemberException());
    int activeBorrows = borrowRepository.countByMemberIdAndReturnDateIsNull(member.getId());
    if (activeBorrows >= borrowLimit) {
      throw new IllegalStateException("Cannot borrow more books");
    }

    book.setAmount(book.getAmount() - 1);
    bookRepository.save(book);

    Borrow borrow = new Borrow();
    borrow.setBook(book);
    borrow.setMember(member);
    borrow.setBorrowDate(LocalDateTime.now());
    borrowRepository.save(borrow);
  }

  @Transactional
  public void returnBook(BorrowDto req) {
    Borrow borrow = borrowRepository.findByMemberIdAndBookIdAndReturnDateIsNull(req.getMemberId(), req.getBookId())
        .orElseThrow(() -> new NoSuchBorrowException());
    Book book = borrow.getBook();
    book.setAmount(book.getAmount() + 1);
    bookRepository.save(book);
    borrow.setReturnDate(LocalDateTime.now());
  }

}
