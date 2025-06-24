package ua.dev.techtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.dev.techtask.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    
}
