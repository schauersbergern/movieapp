package com.task.movie.domain

import com.task.movie.core.AsyncUseCase
import com.task.movie.domain.entities.Movie

class SearchMovieUseCase(private val repository : MovieRepository) : AsyncUseCase<List<Movie>, String>() {
    override suspend fun run(params: String) = repository.searchMovies(params)
}