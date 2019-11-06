package com.legion1900.moxynews.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.legion1900.moxynews.views.adapters.ArticleAdapter
import com.legion1900.moxynews.presenters.NewsPresenter
import com.legion1900.moxynews.R
import com.legion1900.moxynews.contracts.NewsContract

class NewsfeedActivity : MvpAppCompatActivity(), NewsContract.NewsfeedView {

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    private lateinit var rv: RecyclerView
    private val adapter =
        ArticleAdapter(View.OnClickListener { view ->
            presenter.onArticleClick(
                rv.getChildAdapterPosition(view)
            )
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsfeed)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv = findViewById(R.id.rv_news)
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }

    override fun displayNewsfeed(articles: List<NewsContract.Article>) {
        adapter.updateData(articles)
    }

    override fun displayErrorDialog(visible: Boolean) {
        TODO("create dialog fabric") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Activity> openEntry(activity: Class<T>, article: NewsContract.Article) {
        val showArticle = Intent(this, activity)
        showArticle.putExtra(NewsContract.KEY_EXTRA_ARTICLE, article)
        startActivity(showArticle)
    }
}
