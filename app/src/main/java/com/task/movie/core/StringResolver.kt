package com.task.movie.core

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface StringResolver {
    operator fun invoke(@StringRes res: Int, vararg params: Any): String
    fun resolveString(@StringRes res: Int, vararg params: Any): String
    fun resolvePlural(@PluralsRes res: Int, quantity: Int, vararg params: Any): String
}

@Suppress("SpreadOperator")
internal class StringResolverImpl(private val context: Context) : StringResolver {

    override fun invoke(res: Int, vararg params: Any) = resolveString(res, *params)

    override fun resolveString(res: Int, vararg params: Any) =
        context.resources.getString(res, *params)

    override fun resolvePlural(res: Int, quantity: Int, vararg params: Any): String {
        return context.resources.getQuantityString(res, quantity, *params)
    }
}