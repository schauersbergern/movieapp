package com.task.movie.core

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

abstract class BaseRecyclerAdapter<Action> : ListDelegationAdapter<List<Any>>()