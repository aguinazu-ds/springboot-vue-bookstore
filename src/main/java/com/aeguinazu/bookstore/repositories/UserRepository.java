package com.aeguinazu.bookstore.repositories;

import com.aeguinazu.bookstore.models.usersentities.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BookstoreUser, Long> {
}
