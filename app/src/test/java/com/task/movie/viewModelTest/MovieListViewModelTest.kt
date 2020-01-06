package com.task.movie.viewModelTest

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.task.movie.core.StringResolver
import com.task.movie.domain.GetMoviesUseCase
import com.task.movie.domain.SearchMovieUseCase
import com.task.movie.domain.entities.Movie
import com.task.movie.utils.InstantExecutorExtension
import com.task.movie.utils.TestCoroutineDispatcherExtension
import com.task.movie.viewmodel.MovieListViewModel
import com.task.movie.viewmodel.MovieListViewModelImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class, TestCoroutineDispatcherExtension::class)
class MovieListViewModelTest {

    private lateinit var viewModel : MovieListViewModel

    @MockK
    private lateinit var searchMovieUseCase : SearchMovieUseCase

    @MockK
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @MockK
    private lateinit var stringResolver: StringResolver

    private val titleString = "A string"
    private val movieList = listOf(
        Movie(titleString, "/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg",
            "Very Long Description", 4.2),
        Movie(titleString, "/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg",
            "Very Long Description", 4.2),
        Movie(titleString, "/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg",
            "Very Long Description", 4.2)
    )

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { stringResolver(any()) } returns ""
        coEvery { searchMovieUseCase(any()) } returns  SuspendableResult.of(movieList)
        coEvery { getMoviesUseCase(any()) } returns  SuspendableResult.of(movieList)
        viewModel = MovieListViewModelImpl(getMoviesUseCase, searchMovieUseCase, stringResolver)
    }

    @Test
    fun `viemodel is initialized with movies`() = runBlocking {
        viewModel.loadMovieList()
        assertEquals(movieList, viewModel.currentState.movieList)
    }

    @Test
    fun `movies cannot be searched with less characters`() = runBlocking {
        val errorText = "Please add longer Query"
        every { stringResolver(any()) } returns errorText
        viewModel.searchMovie("te")
        assertEquals(listOf<Movie>(), viewModel.currentState.movieList)
        assertEquals(errorText, viewModel.currentState.errorText)
    }

    @Test
    fun `movies can be searched with more than searchlimit characters`() = runBlocking {
        viewModel.searchMovie("test")
        assertEquals(movieList, viewModel.currentState.movieList)
    }

}