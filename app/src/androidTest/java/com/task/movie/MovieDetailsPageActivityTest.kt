package com.task.movie

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.movie.utilis.Constants
import com.task.movie.view.MovieDetailsPageActivity
import com.task.movie.viewmodel.MovieDetailViewModel
import com.task.movie.viewmodel.MovieDetailViewModelImpl
import io.mockk.MockKAnnotations
import org.hamcrest.CoreMatchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MovieDetailsPageActivityTest {

    private lateinit var viewModel: MovieDetailViewModel

    private val moviename = "moviename"
    private val desc = "desc"
    private val posterpath = "/fgGzTEoNxptCRtEOpOPvIEdlxAq.jpg"
    private val ratingvalue = 1.1

    fun initSuccessState() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        loadKoinModules(module(override = true) { viewModel { viewModel } })

        viewModel = MovieDetailViewModelImpl()

        val intent = Intent(ApplicationProvider.getApplicationContext(), MovieDetailsPageActivity::class.java)
            .putExtra(Constants.TAG_MovieName, moviename)
            .putExtra(Constants.TAG_DESC, desc)
            .putExtra(Constants.TAG_IMAGE, posterpath)
            .putExtra(Constants.TAG_RATINGBAR, ratingvalue)
        launchActivity<MovieDetailsPageActivity>(intent)
    }

    @Test
    fun testSuccessState() {

        initSuccessState()

        onView(ViewMatchers.withId(R.id.txtView_MovieName)).check(matches(withText(containsString(moviename))))
        onView(ViewMatchers.withId(R.id.txtView_MovieDesc)).check(matches(withText(containsString(desc))))

    }
}