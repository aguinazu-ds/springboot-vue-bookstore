package com.aeguinazu.bookstore.repositories;

import com.aeguinazu.bookstore.models.usersentities.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookstoreUserRepository extends JpaRepository<BookstoreUser, Long> {
//    Optional<BookstoreUser> findById(Long id);
    Optional<BookstoreUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
