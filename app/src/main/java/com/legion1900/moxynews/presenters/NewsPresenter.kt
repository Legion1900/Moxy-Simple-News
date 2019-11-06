package com.legion1900.moxynews.presenters

import com.arellomobile.mvp.MvpPresenter
import com.legion1900.moxynews.contracts.NewsContract

class NewsPresenter : MvpPresenter<NewsContract.NewsfeedView>(), NewsContract.Presenter {
    override fun updateNewsfeed(topic: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onArticleClick(articleInd: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}