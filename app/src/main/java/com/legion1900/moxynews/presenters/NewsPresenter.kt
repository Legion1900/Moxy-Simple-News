package com.legion1900.moxynews.presenters

import com.arellomobile.mvp.MvpPresenter
import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.models.CachingNewsRepository
import java.util.*

class NewsPresenter : MvpPresenter<NewsContract.NewsfeedView>(), NewsContract.Presenter {

    private val view = viewState

    private val onFailure: () -> Unit = { view.displayErrorDialog(true) }
    private val onStart: () -> Unit = { /*TODO: call some loading animation from swipe-refresh*/ }
    private val onLoaded: (NewsContract.Response) -> Unit = {
        /*TODO: stop loading animation from swipe-refresh*/
        view.displayErrorDialog(false)
        view.displayNewsfeed(it.articles)
    }

    private val repo = CachingNewsRepository(
        onStartCallback = onStart,
        onLoaded = onLoaded,
        onFailureCallback = onFailure
    )

    override fun updateNewsfeed(topic: String) {
        repo.loadNews(topic, getCurrentDate())
    }

    override fun onArticleClick(articleInd: Int) {
        val article = repo.response!!.articles[articleInd]
        // TODO: implement ArticleActivity and launch it from here.
    }

    private fun getCurrentDate() = Calendar.getInstance().time
}