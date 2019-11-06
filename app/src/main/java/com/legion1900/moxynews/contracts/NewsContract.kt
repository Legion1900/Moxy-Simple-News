package com.legion1900.moxynews.contracts

import android.app.Activity
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

object NewsContract {
    interface NewsfeedView : MvpView {
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun displayNewsfeed(articles: List<Article>)
        @StateStrategyType(SkipStrategy::class)
        fun setProgressBar(enabled: Boolean)
        /*
        * Launches activity to display article. Must be ignored by ViewState queue.
        * */
        @StateStrategyType(SkipStrategy::class)
        fun <T : Activity> openEntry(activity: Class<T>)
    }

    interface ArticleView : MvpView {
        @StateStrategyType(SingleStateStrategy::class)
        fun displayArticle(article: Article)
    }

    interface Presenter {
        fun updateNewsfeed(topic: String)
        /*
        * articleInd - index of article that was clicked in a List<Article>.
        */
        fun onArticleClick(articleInd: Int)
        /*
        * Provides article data for ArticleView.
        * */
        fun loadArticle(articleInd: Int)
    }

    interface NewsfeedModel {
        val topic: String?
        val news: List<Article>?
        /*
        * Asks to update cached in 'news' property articles.
        * */
        fun loadNews(topic: String)
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
        val author: String
        val title: String
        val publishedAt: String
        val sourceName: String
        val urlToImage: String
        val description: String
    }
}