package com.legion1900.moxynews.nework.data

import com.legion1900.moxynews.contracts.NewsContract

data class Response(
    override val status: String,
    override val code: String,
    override val message: String,
    override val articles: List<Article>
) : NewsContract.Response