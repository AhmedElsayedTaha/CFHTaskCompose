package com.cfh.cfhtaskcompose.intent

sealed class MoviesIntent {
    class PopularMoviesIntent(val language: String,val includeAdult: Boolean): MoviesIntent()
}