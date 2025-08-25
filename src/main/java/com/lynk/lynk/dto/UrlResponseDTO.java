package com.lynk.lynk.dto;

public class UrlResponseDTO {
    private String originalUrl;
    private String shortUrl;

    public UrlResponseDTO() {} // required by Jackson

    public UrlResponseDTO(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }
}
