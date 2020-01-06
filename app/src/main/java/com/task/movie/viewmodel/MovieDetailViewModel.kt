package com.task.movie.viewmodel

import com.task.movie.core.BaseViewModel
import com.task.movie.domain.entities.Movie


data class MovieDetailState(val movie: Movie?)

abstract class MovieDetailViewModel: BaseViewModel<MovieDetailState>(MovieDetailState(movie = null)) {
    abstract fun init(movie: Movie)
}

internal class MovieDetailViewModelImpl : MovieDetailViewModel() {
    override fun init(movie: Movie) {
        setState { copy(movie = movie) }
    }
}