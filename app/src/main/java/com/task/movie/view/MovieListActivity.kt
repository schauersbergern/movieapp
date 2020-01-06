package com.task.movie.view

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.snackbar.Snackbar
import com.task.movie.R
import com.task.movie.databinding.ActivityMovielistBinding
import com.task.movie.domain.entities.Movie
import com.task.movie.utilis.Constants
import com.task.movie.view.adapter.MovieListAction
import com.task.movie.view.adapter.MovieListAdapter
import com.task.movie.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.activity_movielist.*

class MovieListActivity : AppCompatActivity() {

    private val viewModel: MovieListViewModel by viewModel()

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMovielistBinding = DataBindingUtil.setContentView(this, R.layout.activity_movielist)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()
        initSearchView()
        viewModel.loadMovieList()
    }

    private fun setupRecyclerView() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        movie_list.layoutManager = linearLayoutManager

        movie_list.adapter = MovieListAdapter { action ->
            when (action) {
                is MovieListAction.SelectMovie -> {
                    openMovieDetail(action.movie)
                }
            }
        }

        movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()

                recyclerView.layoutManager?.itemCount.let { totalItemCount ->
                    if (totalItemCount != null) {
                        if ((lastVisibleItemPosition + 1) >= totalItemCount) {
                            viewModel.loadMore()
                        }
                    }
                }
            }
        })

    }

    private fun initSearchView() {


        searchView_movies.setOnClickListener { searchView_movies.isIconified = false }

        searchView_movies.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val search = newText ?: ""
                viewModel.searchMovie(search)
                return true
            }
        })

        searchView_movies.setOnCloseListener {
            viewModel.loadMovieList()
            false
        }
    }

    private fun openMovieDetail(movie: Movie) {
        val intent = Intent(baseContext, MovieDetailsPageActivity::class.java)
        intent.putExtra(Constants.TAG_MovieName, movie.title)
        intent.putExtra(Constants.TAG_DESC, movie.description)
        intent.putExtra(Constants.TAG_IMAGE, movie.pictureUrl)
        intent.putExtra(Constants.TAG_RATINGBAR, movie.voteAverage)
        startActivity(intent)
    }
}


