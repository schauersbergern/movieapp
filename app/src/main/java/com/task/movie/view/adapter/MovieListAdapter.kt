package com.task.movie.view.adapter

import com.task.movie.core.BaseRecyclerAdapter

 class MovieListAdapter(onItemClicked: (MovieListAction) -> Unit) :
     BaseRecyclerAdapter<MovieListAction>() {
     init {
         delegatesManager.addDelegate(MovieListAdapterDelegate(onItemClicked))
     }
}
