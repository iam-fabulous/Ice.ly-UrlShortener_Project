import { configureStore } from '@reduxjs/toolkit';
import { urlShortenerApi } from '../Service/UrlShortenerApi';

const store = configureStore({
    reducer:{
        [urlShortenerApi.reducerPath] : urlShortenerApi.reducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(urlShortenerApi.middleware)
})

export default store;