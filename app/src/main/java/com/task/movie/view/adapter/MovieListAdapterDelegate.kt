package com.task.movie.view.adapter

import com.task.movie.R
import com.task.movie.core.BaseRecyclerAdapterDelegate
import com.task.movie.databinding.MovieListItemBinding
import com.task.movie.domain.entities.Movie

sealed class MovieListAction {
    data class SelectMovie(val movie : Movie) : MovieListAction()
}

class MovieListAdapterDelegate(onItemClicked : (MovieListAction) -> Unit) : BaseRecyclerAdapterDelegate<Movie, MovieListItemBinding, MovieListAction>
    (R.layout.movie_list_item, onItemClicked){
    override fun bindViewHolder(
        binding: MovieListItemBinding,
        obj: Movie,
        position: Int,
        dataSize: Int,
        onItemClicked: (MovieListAction) -> Unit
    ) {
        binding.movie = obj
        binding.root.setOnClickListener{ onItemClicked(MovieListAction.SelectMovie(obj)) }
    }

    override fun isForViewType(items: List<Any>, position: Int) = items[position] is Movie
}