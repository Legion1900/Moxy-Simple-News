package com.legion1900.moxynews.contracts

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import java.util.*

object NewsContract {

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
        fun openEntry(activity: Class<*>)
    }

    interface ArticleView : MvpView {
        @StateStrategyType(SingleStateStrategy::class)
        fun displayArticle(article: Article)
    }

    interface NewsfeedPresenter {
        fun updateNewsfeed(topic: String)
        /*
        * articleInd - index of article that was clicked in a List<Article>.
        */
        fun onArticleClick(articleInd: Int)
    }

    interface ArticlePresenter {
        fun provideData()
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
    * Workaround for sharing chosen article from NewsfeedView.
    * */
    interface ArticleRepo {
        var article: Article
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

    interface Article {
        val author: String?
        val title: String?
        val publishedAt: String?
        val sourceName: String?
        val urlToImage: String?
        val description: String?
    }
}