package com.task.movie.useCaseTest

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.task.movie.domain.SearchMovieUseCase
import com.task.movie.domain.MovieRepository
import com.task.movie.domain.entities.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchMovieUseCaseTest {

    private lateinit var useCase: SearchMovieUseCase

    @MockK
    private lateinit var movieRepository: MovieRepository

    private val movie = Movie("", "", "", 0.0)

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = SearchMovieUseCase(movieRepository)
    }

    @Test
    fun `search returns Movie Entities`() = runBlocking {

        coEvery {
            movieRepository.searchMovies(any())
        } returns SuspendableResult.of(listOf(movie, movie, movie))

        val result = useCase("Test")

        Assert.assertThat(result, CoreMatchers.instanceOf(SuspendableResult.Success::class.java))
        Assertions.assertEquals(movie, result.get().first())
        Assertions.assertEquals(result.get().size, 3)
    }
}