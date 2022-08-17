package com.aeguinazu.bookstore.repositories;

import com.aeguinazu.bookstore.models.usersentities.BookstoreUser;
import com.aeguinazu.bookstore.models.usersentities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(BookstoreUser bookstoreUser);
}
