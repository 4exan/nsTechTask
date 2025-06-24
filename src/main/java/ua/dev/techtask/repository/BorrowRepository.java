package ua.dev.techtask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.dev.techtask.entity.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
  Optional<Borrow> findByMemberIdAndBookIdAndReturnDateIsNull(Long memberId, Long bookId);

  int countByMemberIdAndReturnDateIsNull(Long memberId);

  int countByBookIdAndReturnDateIsNull(Long bookId);
}
