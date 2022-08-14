package com.aeguinazu.bookstore.repositories;

import com.aeguinazu.bookstore.models.bookentities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByGenre(String genre);
    Genre findByGenre(String genre);
}
