import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [longUrl, setLongUrl] = useState("");
  const [urls, setUrls] = useState([]);
  const [copiedIndex, setCopiedIndex] = useState(null);

  // Load previously shortened URLs from localStorage
  useEffect(() => {
    const storedUrls = JSON.parse(localStorage.getItem("shortenedUrls")) || [];
    setUrls(storedUrls);
  }, []);

  const handleShorten = async () => {
    if (!longUrl) return;

    try {
      const response = await axios.post("/api/url/shorten", { originalUrl: longUrl });
      const newUrl = response.data.shortUrl;

      const updatedUrls = [...urls, newUrl];
      setUrls(updatedUrls);
      localStorage.setItem("shortenedUrls", JSON.stringify(updatedUrls));
      setLongUrl("");
    } catch (error) {
      console.error("Error shortening URL:", error);
    }
  };

  const handleCopy = (url, index) => {
    navigator.clipboard.writeText(url);
    setCopiedIndex(index);
    setTimeout(() => setCopiedIndex(null), 2000);
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

        {urls.length > 0 && (
          <div className="result">
            <p>Shortened URLs:</p>
            {urls.map((url, idx) => (
              <div key={idx} className="short-link">
                <a href={url} target="_blank" rel="noreferrer">{url}</a>
                <button
                  className={`copy-btn ${copiedIndex === idx ? "copied" : ""}`}
                  onClick={() => handleCopy(url, idx)}
                >
                  {copiedIndex === idx ? "Copied!" : "Copy"}
                </button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
