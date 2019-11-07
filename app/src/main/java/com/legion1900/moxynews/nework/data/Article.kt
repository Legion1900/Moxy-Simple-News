package com.legion1900.moxynews.nework.data

import android.os.Parcel
import android.os.Parcelable
import com.legion1900.moxynews.contracts.NewsContract

class Article(
    override val author: String,
    override val title: String,
    override val publishedAt: String,
    override val sourceName: String,
    override val urlToImage: String,
    override val description: String
) : NewsContract.Article {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //TODO: clean this after testing ArticleActivity
//        super.writeToParcel(parcel, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(publishedAt)
        parcel.writeString(sourceName)
        parcel.writeString(urlToImage)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}