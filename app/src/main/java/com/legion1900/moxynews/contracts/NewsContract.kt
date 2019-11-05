package com.legion1900.moxynews.contracts

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

object NewsContract {
    interface NewsfeedView : MvpView {
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun displayNewsfeed(authors: List<String>, titles: List<String>, dates: List<String>)
        @StateStrategyType(SkipStrategy::class)
        fun setProgressBar(enabled: Boolean)
        /*
        * Launches activity to display article. Must be ignored by ViewState queue.
        * */
        @StateStrategyType(SkipStrategy::class)
        fun openEntry(title: String, sourceName: String, description: String)
    }

    interface ArticleModel {
        val author: String
        val title: String
        val publishedAt: String
        val sourceName: String
        val urlToImage: String
        val description: String
    }

    interface Presenter {
        fun update(topic: String)
        fun onEntryClick(entryInd: Int)
    }
}