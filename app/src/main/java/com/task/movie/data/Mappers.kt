package com.task.movie.data

import com.task.movie.domain.entities.Movie
import com.task.movie.model.Result

fun Result.toEntity() : Movie {
    return Movie(title, posterPath, overview, voteAverage)
}