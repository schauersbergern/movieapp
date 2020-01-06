package com.task.movie.domain

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.task.movie.domain.entities.Movie

interface MovieRepository {
    suspend fun getMovies(page: Int) : SuspendableResult<List<Movie>, Exception>
    suspend fun searchMovies(searchText: String) : SuspendableResult<List<Movie>, Exception>
}