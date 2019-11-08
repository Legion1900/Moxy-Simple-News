package com.legion1900.moxynews.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.legion1900.moxynews.R
import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.presenters.ArticlePresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class ArticleActivity : MvpAppCompatActivity(), NewsContract.ArticleView {

    @InjectPresenter
    lateinit var presenter: ArticlePresenter

    private val picasso = Picasso.get()
    private lateinit var glide: RequestManager

    private lateinit var ivPicasso: ImageView
    private lateinit var ivGlide: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvSource: TextView
    private lateinit var tvDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        setSupportActionBar(toolbar)

        findViews()
        glide = Glide.with(this)
        presenter.provideData()
    }

    private fun findViews() {
        tvTitle = findViewById(R.id.tv_title)
        tvSource = findViewById(R.id.tv_source_name)
        tvDescription = findViewById(R.id.tv_description)
        ivPicasso = findViewById(R.id.iv_picasso)
        ivGlide = findViewById(R.id.iv_glide)
    }

    private fun initTextViews(article: NewsContract.Article) {
        tvTitle.text = getString(R.string.title, article.title)
        tvSource.text = getString(R.string.source, article.sourceName)
        tvDescription.text = article.description
    }

    private fun initImageViews(url: String?) {
        if (url != null)
        {
            picasso.load(url)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(ivPicasso)
            glide.load(url)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .into(ivGlide)
        } else {
            val group = findViewById<LinearLayout>(R.id.group_pictures)
            group.visibility = View.GONE
        }
    }

    override fun displayArticle(article: NewsContract.Article) {
        initTextViews(article)
        initImageViews(article.urlToImage)
    }
}
