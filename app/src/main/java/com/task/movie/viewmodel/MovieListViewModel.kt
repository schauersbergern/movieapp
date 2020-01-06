package com.task.movie.viewmodel

import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.coroutines.success
import com.task.movie.R
import com.task.movie.core.BaseViewModel
import com.task.movie.core.StringResolver
import com.task.movie.domain.GetMoviesUseCase
import com.task.movie.domain.SearchMovieUseCase
import com.task.movie.domain.entities.Movie
import com.task.movie.utilis.Constants.VM_MIN_SEARCH_LENGHT
import com.task.movie.utilis.add
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

data class MovieListState(
    val movieList : List<Movie> = listOf(),
    var showLoading : Boolean = true,
    var currentPage : Int = 1,
    var errorText : String? = null
)

abstract class MovieListViewModel: BaseViewModel<MovieListState>(MovieListState()) {
    abstract fun loadMovieList()
    abstract fun loadMore()
    abstract fun searchMovie(searchText: String)
}

internal class MovieListViewModelImpl (
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val stringResolver: StringResolver
    ) : MovieListViewModel() {

    private var searchJob : Job? = null

    override fun loadMovieList() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            getState {
                getMoviesUseCase(it.currentPage).fold( {
                    setState { copy(movieList = it, showLoading = false, errorText = null) }
                }, {
                    setState{ copy( errorText = it.toString())}
                } )
            }
        }
    }

    override fun loadMore() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            getState {currentState ->
                var newPage = currentState.currentPage + 1
                getMoviesUseCase(newPage).success {
                    setState {
                        copy(
                            movieList = currentState.movieList.add(it),
                            showLoading = false,
                            currentPage = newPage,
                            errorText = null
                        )
                    }
                }
            }
        }
    }

    override fun searchMovie(searchText: String) {
        searchJob?.cancel()

        setState { copy( movieList = listOf(), showLoading = true, errorText = null ) }

        if (searchText.isEmpty()) {
            loadMovieList()
        } else if (searchText.length > VM_MIN_SEARCH_LENGHT) {
            searchJob = viewModelScope.launch {
                searchMovieUseCase(searchText).fold( {
                    setState { copy(movieList = it, currentPage = 1, errorText = null) }
                }, {
                    setState{ copy( errorText = it.toString())}
                })
            }
        } else {
            setState { copy(
                movieList = listOf(),
                showLoading = false,
                errorText = stringResolver(R.string.errorQueryTooShort))
            }
        }

    }
}