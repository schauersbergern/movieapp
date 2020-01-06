package com.task.movie.networking

import com.task.movie.model.MovieApiResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInterface {

    @GET("movie/now_playing?")
    fun getMovieList(@Query("page")mPage:Int) : Call<MovieApiResponseModel>

    @GET("search/movie?")
    fun searchMovieName(
        @Query("query")mquery:String,
        @Query("page")mPage:Int
    ): Call<MovieApiResponseModel>

}