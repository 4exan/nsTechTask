package ua.dev.techtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.dev.techtask.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    
}
