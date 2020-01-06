package com.task.movie.domain

import com.task.movie.core.AsyncUseCase
import com.task.movie.domain.entities.Movie

class GetMoviesUseCase(private val repository : MovieRepository) : AsyncUseCase<List<Movie>, Int>() {
    override suspend fun run(params: Int) = repository.getMovies(params)
}