package com.legion1900.moxynews.contracts

import android.os.Parcelable
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.util.*

object NewsContract {

    const val KEY_EXTRA_ARTICLE = "Article"

    interface NewsfeedView : MvpView {
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun displayNewsfeed(articles: List<Article>)

        @StateStrategyType(SkipStrategy::class)
        fun displayErrorDialog()

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun setLoadingAnimation(isLoading: Boolean)

        /*
        * Launches activity to display article. Must be ignored by ViewState queue.
        * */
        @StateStrategyType(SkipStrategy::class)
        fun openEntry(activity: Class<*>, article: Article)
    }

    interface Presenter {
        fun updateNewsfeed(topic: String)
        /*
        * articleInd - index of article that was clicked in a List<Article>.
        */
        fun onArticleClick(articleInd: Int)
    }

    interface NewsfeedModel {
        /*
        * Async callbacks.
        * */
        val onStartCallback: () -> Unit
        val onLoadedCallback: (Response) -> Unit
        val onFailureCallback: () -> Unit
        /*
        * Asks to update cached in 'news' property articles.
        * */
        fun loadNews(topic: String, date: Date)
    }

    /*
    * Interfaces that describes data classes.
    * */

    interface Response {
        val status: String
        val code: String
        val message: String
        val articles: List<Article>
    }

    interface Article : Parcelable {
        val author: String
        val title: String
        val publishedAt: String
        val sourceName: String
        val urlToImage: String
        val description: String
    }
}