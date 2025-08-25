package com.lynk.lynk.repository;

import com.lynk.lynk.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
}
