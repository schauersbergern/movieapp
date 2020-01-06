package com.task.movie.utilis

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.squareup.picasso.Picasso
import com.task.movie.R

@BindingAdapter("showIf")
fun showIf(view : View, show : Boolean) {
    if (show) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("entries")
fun <T> RecyclerView.setEntries(entries: List<T>?) {
    when {
        entries == null -> return
        adapter is AbsDelegationAdapter<*> -> (adapter as? AbsDelegationAdapter<List<T>>)?.run {
            items = entries
            notifyDataSetChanged()
        }
    }
}

@BindingAdapter(value = ["app:progressScaled"], requireAll = true)
fun setProgress(progressBar: ProgressBar, max: Double) {
    progressBar.progress = max.div(2).toInt()
}

@BindingAdapter("image")
fun setPosterPath(view: ImageView, imageURL: String?) {
    Picasso.get()
        .load(Constants.TAG_IMAGEURL + imageURL)
        .placeholder(R.drawable.ic_launcher_background)
        .into(view)

}

@BindingAdapter("ratingBar")
fun setRatingBarStatus(view: RatingBar, ratingBar: Double?) {
    if (!ratingBar!!.equals(0.0)) {
        val value = ratingBar.div(2)
        view.rating = value.toFloat()
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}