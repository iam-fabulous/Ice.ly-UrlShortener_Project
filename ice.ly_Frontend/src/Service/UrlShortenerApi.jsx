// src/api/urlShortenerApi.jsx
import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

// Define the API service
export const urlShortenerApi = createApi({
  reducerPath: "urlShortenerApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8080" }), // Backend base URL
  endpoints: (builder) => ({
    // Endpoint for generating short URL
    generateShortUrl: builder.mutation({
      query: (originalUrl) => ({
        url: "/generateShortUrl",
        method: "POST",
        body: { originalUrl },
      }),
    }),

    // Add more endpoints here as needed
    // Example: generateQRCode, fetchUserDetails, etc.
    fetchOriginalUrl: builder.mutation({
      query: (fetchOriginalUrl) => ({
        url: "/fetchOriginalUrl",
        method: "POST",
        body: { fetchOriginalUrl },
      }),
    }),
  }),
});

// Export hooks for usage in components
export const { useGenerateShortUrlMutation, useFetchOriginalUrlMutation } =
  urlShortenerApi;
