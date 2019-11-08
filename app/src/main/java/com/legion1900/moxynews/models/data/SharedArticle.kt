package com.legion1900.moxynews.models.data

import com.legion1900.moxynews.contracts.NewsContract

object SharedArticle : NewsContract.ArticleRepo {
    override lateinit var article: NewsContract.Article
}