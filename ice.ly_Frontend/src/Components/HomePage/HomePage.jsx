import React, { useState } from "react";
import Url_Link from "../../assets/link-solid.svg";
import style from "../../Components/HomePage/HomePage.module.css";
import {
  useFetchOriginalUrlMutation,
  useGenerateShortUrlMutation,
} from "../../Service/UrlShortenerApi.jsx";

const HomePage = () => {
  const [originalUrl, setOriginalUrl] = useState("");
  const [shortenedUrl, setShortenedUrl] = useState("");
  const [error, setError] = useState("");

  // Use the mutation hook from RTK Query
  const [generateShortUrl, { isLoading, isError }] =
    useGenerateShortUrlMutation();
  const [fetchOriginalUrl] = useFetchOriginalUrlMutation();

  const handleGenerateShortUrl = async (e) => {
    e.preventDefault(); // Prevent form reload
    try {
      const result = await generateShortUrl(originalUrl).unwrap(); // RTK Query API
      // Extract shortUrl from the result and update the state
      const { shortUrl } = result;
      setShortenedUrl(shortUrl); // Set the shortened URL
      setError(""); // Clear any previous error
    } catch (err) {
      setError("Failed to shorten URL");
      setShortenedUrl(""); // Clear shortened URL in case of error
    }
  };

  const handleRedirect = async (shortUrl) => {
    try {
      const response = await fetch("http://localhost:8080/fetchOriginalUrl", {
        method: "POST", // Send a POST request
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ shortUrl: shortUrl }), // Send full short URL as JSON
      });

      const data = await response.json();

      if (response.ok && data.originalUrl) {
        window.location.href = data.originalUrl; // Redirect user to the original URL
      } else {
        setError("Failed to fetch the original URL");
      }
    } catch (err) {
      setError("An error occurred while fetching the URL");
    }
  };

  return (
    <div className={style.main_container}>
      <div className={style.sub_container}>
        <div className={style.welcome}>
          <h3>
            Welcome to <span>ice.ly</span>
          </h3>
          <p>Customize your links with ease.</p>
        </div>

        <div className={style.link_container}>
          <h2>
            Shorten a long link <img src={Url_Link} alt="URL Icon" />
          </h2>

          {/* Form for URL shortening */}
          <form className={style.link} onSubmit={handleGenerateShortUrl}>
            <span>Paste your long link here</span>
            <input
              type="text"
              placeholder="http://example.com/my-long-url"
              value={originalUrl}
              onChange={(e) => setOriginalUrl(e.target.value)}
              required
            />
            <div className={style.link_button}>
              <img src={Url_Link} alt="" />
              <button type="submit" disabled={isLoading}>
                Get your link for free
              </button>
            </div>
          </form>
        </div>

        {/* Loading state */}
        {isLoading && <p>Loading...</p>}

        {/* Error state */}
        {isError && <p style={{ color: "red" }}>Error occurred!</p>}
        {error && <p style={{ color: "red" }}>{error}</p>}

        {/* Shortened URL Display */}
        {shortenedUrl && (
          <div className={style.shortened_url_container}>
            <h2>Shortened URL:</h2>
            <a onClick={() => handleRedirect(shortenedUrl)}>{shortenedUrl}</a>
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
