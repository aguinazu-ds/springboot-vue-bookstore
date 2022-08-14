package com.aeguinazu.bookstore.repositories;

import com.aeguinazu.bookstore.models.bookentities.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    boolean existsByAuthorName(String authorName);
    BookAuthor findByAuthorName(String authorName);
}
