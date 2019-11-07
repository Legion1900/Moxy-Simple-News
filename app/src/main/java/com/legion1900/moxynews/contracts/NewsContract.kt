package com.legion1900.moxynews.contracts

import android.app.Activity
import android.os.Parcelable
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import java.util.*

object NewsContract {

    const val KEY_EXTRA_ARTICLE = "Article"

    interface NewsfeedView : MvpView {
        @StateStrategyType(AddToEndSingleStrategy::class)
        fun displayNewsfeed(articles: List<Article>)

        @StateStrategyType(SkipStrategy::class)
        fun displayErrorDialog(visible: Boolean)

        /*
        * Launches activity to display article. Must be ignored by ViewState queue.
        * */
        @StateStrategyType(SkipStrategy::class)
        fun <T : Activity> openEntry(activity: Class<T>, article: Article)
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
    }

    interface NewsfeedModel {
        /*
        * Async callbacks.
        * */
        val onStartCallback: () -> Unit
        val onLoadedCallback: (Response) -> Unit
        val onFailureCallback: () -> Unit
        /*
        * Properties for caching purpose.
        * */
        var topic: String?
        var timestamp: Date?
        var response: Response?
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