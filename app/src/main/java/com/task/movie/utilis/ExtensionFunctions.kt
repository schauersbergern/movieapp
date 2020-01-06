package com.task.movie.utilis

import com.task.movie.domain.entities.Movie

fun List<Movie>.add(listToAdd: List<Movie>) : List<Movie> {
    val list = mutableListOf<Movie>()
    list.addAll(this)
    list.addAll(listToAdd)
    return list
}