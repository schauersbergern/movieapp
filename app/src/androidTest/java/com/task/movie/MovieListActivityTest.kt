package com.task.movie

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.kittinunf.result.coroutines.SuspendableResult
import com.task.movie.core.StringResolver
import com.task.movie.domain.GetMoviesUseCase
import com.task.movie.domain.SearchMovieUseCase
import com.task.movie.domain.entities.Movie
import com.task.movie.utils.withItemCount
import com.task.movie.utils.withRecyclerView
import com.task.movie.view.MovieListActivity
import com.task.movie.viewmodel.MovieListViewModel
import com.task.movie.viewmodel.MovieListViewModelImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MovieListActivityTest {

    private lateinit var viewModel: MovieListViewModel

    @MockK
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @MockK
    private lateinit var searchMovieUseCase: SearchMovieUseCase

    @MockK
    private lateinit var stringResolver: StringResolver

    private val titleString = "Title"
    private val movieList = listOf(
        Movie(titleString, "/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg",
            "Very Long Description", 4.2),
                Movie(titleString, "/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg",
        "Very Long Description", 4.2),
        Movie(titleString, "/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg",
            "Very Long Description", 4.2)
    )


    fun init() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        loadKoinModules(module(override = true) { viewModel { viewModel } })
        coEvery { getMoviesUseCase(any()) } returns SuspendableResult.of(listOf())
        every { stringResolver(any()) } returns ""
        viewModel = MovieListViewModelImpl(getMoviesUseCase, searchMovieUseCase, stringResolver)
        launchActivity<MovieListActivity>()
    }


    @Test
    fun initStateTest() {
        init()

        coEvery { getMoviesUseCase(any()) } returns SuspendableResult.Success(
            movieList
        )

        onView(withId(R.id.movie_list)).check(
            matches(
                withEffectiveVisibility(Visibility.VISIBLE)
            )
        )

        onView(withId(R.id.movie_list)).check(
            matches(
                withItemCount(movieList.size)
            )
        )

        onView(withRecyclerView(R.id.movie_list)?.atPositionOnView(1, R.id.txtView_Tittle)).check(
            matches(
                withText(containsString(titleString)
            ))
        )
    }

    @Test
    fun testSearchTooShort() {
        init()

        val errorTextTooShort = "tooshort"
        every { stringResolver(any()) } returns errorTextTooShort

        onView(withId(R.id.searchView_movies)).perform(click())
        onView(withId(R.id.searchView_movies)).perform(typeText("s"))

        onView(withId(R.id.movie_list)).check(
            matches(withEffectiveVisibility(Visibility.GONE))
        )

        onView(withId(R.id.errorText)).check(
            matches(withEffectiveVisibility(Visibility.VISIBLE))
        )

        onView(withId(R.id.errorText)).check(
            matches(withText(containsString(errorTextTooShort)))
        )
    }

    @Test
    fun testSearch() {
        init()

        coEvery { searchMovieUseCase(any()) } returns SuspendableResult.Success(
            movieList
        )

        onView(withId(R.id.searchView_movies)).perform(click())
        onView(withId(R.id.searchView_movies)).perform(typeText("search"))

        onView(withId(R.id.movie_list)).check(
            matches(withEffectiveVisibility(Visibility.VISIBLE))
        )

        onView(withId(R.id.movie_list)).check(
            matches(withItemCount(movieList.size))
        )

    }
}
