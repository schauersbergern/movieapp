package com.task.movie.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher? {
    return RecyclerViewMatcher(recyclerViewId)
}

fun withItemCount(amount: Int): Matcher<Any> =
    object : BoundedMatcher<Any, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with item count: ").appendValue(amount)
        }

        override fun matchesSafely(item: RecyclerView) = item.adapter?.itemCount == amount
    }