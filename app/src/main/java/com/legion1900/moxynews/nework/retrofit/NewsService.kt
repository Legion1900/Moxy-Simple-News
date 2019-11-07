package com.legion1900.moxynews.nework.retrofit;

import com.legion1900.moxynews.BuildConfig
import com.legion1900.moxynews.nework.data.Response
import com.legion1900.moxynews.utils.TimeUtils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

internal interface NewsService {
    @GET("v2/everything")
    fun queryNews(@QueryMap options: kotlin.collections.Map<String, String>): Call<Response>

    companion object {
        private const val KEY_TOPIC = "q"
        private const val KEY_DATE = "from"
        private const val KEY_SORT = "sortBy"
        private const val KEY_API_KEY = "apiKey"
        private const val VALUE_SORT = "publishedAt"

        private const val TIME_FORMAT = "yyyy-mm-dd"

        fun buildQuery(topic: String, date: Date): kotlin.collections.Map<String, String> {
            val query = HashMap<String, String>()
            query[KEY_TOPIC] = topic
            query[KEY_DATE] = TimeUtils.dateToFormatStr(date, TIME_FORMAT)
            query[KEY_SORT] = VALUE_SORT
            query[KEY_API_KEY] = BuildConfig.apiKey
            return query
        }
    }
}