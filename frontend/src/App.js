import React, { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [longUrl, setLongUrl] = useState("");
  const [shortUrl, setShortUrl] = useState("");
  const [copied, setCopied] = useState(false);

  const handleShorten = async () => {
    try {
      const response = await axios.post("/api/url/shorten", { originalUrl: longUrl });
      setShortUrl(response.data.shortUrl);
    } catch (error) {
      console.error("Error shortening URL:", error);
    }
  };

  const handleCopy = () => {
    if (shortUrl) {
      navigator.clipboard.writeText(shortUrl);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  };

  return (
    <div className="container">
      <div className="card">
        <h1 className="title">URL Shortener</h1>

        <div className="input-group">
          <input
            type="text"
            placeholder="Enter URL"
            value={longUrl}
            onChange={(e) => setLongUrl(e.target.value)}
          />
          <button onClick={handleShorten}>Shorten</button>
        </div>

        {shortUrl && (
          <div className="result">
            <p>Shortened URL:</p>
            <div className="short-link">
              <a href={shortUrl} target="_blank" rel="noreferrer">{shortUrl}</a>
              <button
                className={`copy-btn ${copied ? "copied" : ""}`}
                onClick={handleCopy}
              >
                {copied ? "Copied!" : "Copy"}
              </button>

            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
