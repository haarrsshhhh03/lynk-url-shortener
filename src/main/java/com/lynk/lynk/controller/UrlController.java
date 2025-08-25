package com.lynk.lynk.controller;

import com.lynk.lynk.dto.UrlRequestDTO;
import com.lynk.lynk.dto.UrlResponseDTO;
import com.lynk.lynk.model.Url;
import com.lynk.lynk.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;  // <-- add this import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/url")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestBody UrlRequestDTO request,
                                                     HttpServletRequest req) {
        Url url = urlService.shortenUrl(request.getOriginalUrl());

        // Build full short URL (scheme://host:port + /api/url/{code})
        String shortUrl = req.getScheme() + "://" + req.getServerName()
                + (req.getServerPort() == 80 || req.getServerPort() == 443 ? "" : ":" + req.getServerPort())
                + req.getContextPath()
                + "/api/url/" + url.getShortCode();

        UrlResponseDTO response = new UrlResponseDTO(url.getOriginalUrl(), shortUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        return urlService.getOriginalUrl(shortCode)
                .map(u -> ResponseEntity.status(302).location(URI.create(u.getOriginalUrl())).<Void>build())
                .orElse(ResponseEntity.<Void>notFound().build());
    }
}
