package com.task.movie.useCaseTest

import com.github.kittinunf.result.coroutines.SuspendableResult
import com.task.movie.domain.GetMoviesUseCase
import com.task.movie.domain.MovieRepository
import com.task.movie.domain.entities.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMoviesUseCaseTest {

    private lateinit var useCase: GetMoviesUseCase

    @MockK
    private lateinit var movieRepository: MovieRepository

    private val movie = Movie("", "", "", 0.0)

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetMoviesUseCase(movieRepository)
    }

    @Test
    fun `returns Movie Entities`() = runBlocking {
        coEvery {
            movieRepository.getMovies(any())
        } returns SuspendableResult.of(listOf(movie, movie, movie))

        val result = useCase(0)

        assertThat(result, instanceOf(SuspendableResult.Success::class.java))
        assertEquals(movie, result.get().first())
        assertEquals(result.get().size, 3)
    }

}