package com.legion1900.moxynews.presenters

import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.models.repo.CachingNewsRepository
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class NewsPresenter : MvpPresenter<NewsContract.NewsfeedView>(), NewsContract.Presenter {

    private val onFailure: () -> Unit = {
        viewState.setLoadingAnimation(false)
        viewState.displayErrorDialog()
    }
    private val onStart: () -> Unit = { viewState.setLoadingAnimation(true) }
    private val onLoaded: (NewsContract.Response) -> Unit = {
        viewState.setLoadingAnimation(false)
        viewState.displayNewsfeed(it.articles)
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