package com.aeguinazu.bookstore.repositories;

import com.aeguinazu.bookstore.models.bookentities.BookPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPublisherRepository extends JpaRepository<BookPublisher, Long> {
    boolean existsByPublisher(String publisher);
    BookPublisher findByPublisher(String publisher);
}
