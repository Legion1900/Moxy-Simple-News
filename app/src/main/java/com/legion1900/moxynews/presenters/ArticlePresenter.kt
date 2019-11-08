package com.legion1900.moxynews.presenters

import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.models.data.SharedArticle
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ArticlePresenter : MvpPresenter<NewsContract.ArticleView>(), NewsContract.ArticlePresenter {
    override fun provideData() {
        val article = SharedArticle.article
        viewState.displayArticle(article)
    }
}