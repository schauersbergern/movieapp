package com.task.movie.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.task.movie.R
import com.task.movie.databinding.ActivityMoviedetailsBinding
import com.task.movie.domain.entities.Movie
import com.task.movie.utilis.Constants
import com.task.movie.viewmodel.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsPageActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding : ActivityMoviedetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_moviedetails)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init()
    }

    private fun init() {
        val intent: Intent? = intent
        val bundle = intent!!.extras
        val moviename = bundle!!.getString(Constants.TAG_MovieName)!!
        val desc = bundle.getString(Constants.TAG_DESC)!!
        val posterpath = bundle.getString(Constants.TAG_IMAGE)!!
        val ratingvalue = bundle.getDouble(Constants.TAG_RATINGBAR)

        viewModel.init(Movie(moviename, posterpath, desc, ratingvalue))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}