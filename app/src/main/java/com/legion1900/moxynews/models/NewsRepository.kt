package com.legion1900.moxynews.models

import com.legion1900.moxynews.BuildConfig.apiKey
import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.nework.AsyncExecutor
import com.legion1900.moxynews.utils.TimeUtils
import java.util.*

class NewsRepository(
    override val onStartCallback: () -> Unit,
    onLoaded: (NewsContract.Response) -> Unit,
    override val onFailureCallback: () -> Unit
) : NewsContract.NewsfeedModel {

    private companion object {
        //        TODO: move this to singleton (maybe place those as a companion to NewsService?)
        private const val KEY_TOPIC = "q"
        private const val KEY_DATE = "from"
        private const val KEY_SORT = "sortBy"
        private const val KEY_API_KEY = "apiKey"
        const val VALUE_SORT = "publishedAt"
        /*
        * Minimal timeout in milliseconds before reloading data for the same topic.
        * */
        const val TIMEOUT = 60_000
        const val TIME_FORMAT = "yyyy-mm-dd"

        fun buildQuery(topic: String, date: String): Map<String, String> {
            val query = HashMap<String, String>()
            query[KEY_TOPIC] = topic
            query[KEY_DATE] = date
            query[KEY_SORT] = VALUE_SORT
            query[KEY_API_KEY] = apiKey
            return query
        }
    }

    override var topic: String? = null
    override var timestamp: Date? = null
    override var response: NewsContract.Response? = null

    override val onLoadedCallback: (NewsContract.Response) -> Unit = {
        response = it
        onLoaded(response!!)
    }

    private val executor = AsyncExecutor(onFailureCallback, onLoadedCallback)

    private val timeUtils = TimeUtils()

    override fun loadNews(topic: String, date: Date) {
        if (topic != this.topic || isOutdated(date)) {
            timestamp = date
            startLoading(topic, TimeUtils.dateToFormatStr(date, TIME_FORMAT))
        } else
            onLoadedCallback(response!!)
    }

    /*
    * Should not be called if timestamp == null!
    * */
    private fun isOutdated(newDate: Date): Boolean {
        val delta = timeUtils.subtract(newDate, timestamp!!)
        return delta >= TIMEOUT
    }

    private fun startLoading(topic: String, date: String) {
        onStartCallback()
        val query = buildQuery(topic, date)
        executor.execAsync(query)
    }
}