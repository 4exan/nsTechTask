package ua.dev.techtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.dev.techtask.entity.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {

}
