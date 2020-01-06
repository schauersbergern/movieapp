package com.task.movie.data

import com.github.kittinunf.result.coroutines.map
import com.task.movie.core.handleRetrofitCall
import com.task.movie.domain.MovieRepository
import com.task.movie.networking.MovieInterface

internal class MovieRepositoryImpl(private val remote : MovieInterface) : MovieRepository {
    override suspend fun getMovies(page : Int) = handleRetrofitCall(remote.getMovieList(page)).map {
            it.results.map { result ->
                result.toEntity()
            }
        }

    override suspend fun searchMovies(searchText: String) = handleRetrofitCall(remote.searchMovieName(
        searchText, 1
    )).map {
        it.results.map {result ->
            result.toEntity()
        }
    }
}