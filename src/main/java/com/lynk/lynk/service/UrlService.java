package com.lynk.lynk.service;

import com.lynk.lynk.model.Url;
import com.lynk.lynk.repository.UrlRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // Generate a short code
    private String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    public Url shortenUrl(String originalUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(generateShortCode());
        return urlRepository.save(url);
    }

    public Optional<Url> getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }
}
