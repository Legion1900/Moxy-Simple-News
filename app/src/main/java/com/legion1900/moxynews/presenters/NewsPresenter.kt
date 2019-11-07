package com.legion1900.moxynews.presenters

import com.arellomobile.mvp.MvpPresenter
import com.legion1900.moxynews.contracts.NewsContract

class NewsPresenter : MvpPresenter<NewsContract.NewsfeedView>(), NewsContract.Presenter {

    private val view = viewState

    override fun updateNewsfeed(topic: String) {
        TODO("Ask model to execute query") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onArticleClick(articleInd: Int) {
        TODO("Ask model to give cached articles[articleInd]") //To change body of created functions use File | Settings | File Templates.
    }
}