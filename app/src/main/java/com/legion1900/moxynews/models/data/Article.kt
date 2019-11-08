package com.legion1900.moxynews.models.data

import android.os.Parcel
import com.legion1900.moxynews.contracts.NewsContract

data class Article(
    override val author: String?,
    override val title: String?,
    override val publishedAt: String?,
    override val sourceName: String?,
    override val urlToImage: String?,
    override val description: String?
) : NewsContract.Article {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )
}