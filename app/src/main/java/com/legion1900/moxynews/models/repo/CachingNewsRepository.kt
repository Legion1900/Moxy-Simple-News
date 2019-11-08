package com.legion1900.moxynews.models.repo

import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.models.repo.nework.AsyncExecutor
import com.legion1900.moxynews.models.repo.nework.NewsService
import com.legion1900.moxynews.utils.TimeUtils
import java.util.*

class CachingNewsRepository(
    override val onStartCallback: () -> Unit,
    onLoaded: (NewsContract.Response) -> Unit,
    override val onFailureCallback: () -> Unit
) : NewsContract.NewsfeedModel {

    private companion object {
        /*
        * Minimal timeout in milliseconds before reloading data for the same topic.
        * */
        const val TIMEOUT = 60_000
    }

    private var topic: String? = null
    private var timestamp: Date? = null
    var response: NewsContract.Response? = null
        private set

    override val onLoadedCallback: (NewsContract.Response?) -> Unit = {
        response = it
        response?.let(onLoaded) ?: onFailureCallback()
    }

    private val executor = AsyncExecutor(onLoadedCallback, onFailureCallback)

    private val timeUtils = TimeUtils()

    override fun loadNews(topic: String, date: Date) {
        if (topic != this.topic || isOutdated(date)) {
            timestamp = date
            startLoading(topic, date)
        } else
            onLoadedCallback(response)
    }

    private fun isOutdated(newDate: Date): Boolean {
        return timestamp?.run {
            val delta = timeUtils.subtract(newDate, timestamp!!)
            delta >= TIMEOUT
        } ?: true
    }

    private fun startLoading(topic: String, date: Date) {
        onStartCallback()
        val query = NewsService.buildQuery(topic, date)
        executor.execAsync(query)
    }
}