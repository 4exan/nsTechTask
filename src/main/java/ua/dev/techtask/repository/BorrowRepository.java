package ua.dev.techtask.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.dev.techtask.dto.BorrowedBookStat;
import ua.dev.techtask.entity.Book;
import ua.dev.techtask.entity.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
  Optional<Borrow> findByMemberIdAndBookIdAndReturnDateIsNull(Long memberId, Long bookId);

  @Query("SELECT b.book FROM Borrow b WHERE b.member.name = :name")
  List<Book> findBooksBorrowedByMemberName(@Param("name") String name);

  @Query("SELECT DISTINCT b.book.title FROM Borrow b")
  List<String> findDisctinctBorrowedBookTitles();

  @Query("SELECT new ua.dev.techtask.dto.BorrowedBookStat(b.book.title, COUNT(b.book)) FROM Borrow b GROUP BY b.book.title")
  List<BorrowedBookStat> findBookBorrowStats();

  int countByMemberIdAndReturnDateIsNull(Long memberId);

  int countByBookIdAndReturnDateIsNull(Long bookId);
}
