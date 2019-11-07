package com.legion1900.moxynews.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.legion1900.moxynews.BuildConfig
import com.legion1900.moxynews.R
import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.presenters.NewsPresenter
import com.legion1900.moxynews.utils.DialogFactory
import com.legion1900.moxynews.views.adapters.ArticleAdapter

class NewsfeedActivity : MvpAppCompatActivity(), NewsContract.NewsfeedView {

    companion object {
        const val DIALOG_TAG = BuildConfig.APPLICATION_ID
    }

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    private lateinit var rv: RecyclerView
    private val adapter =
        ArticleAdapter(View.OnClickListener { view ->
            presenter.onArticleClick(
                rv.getChildAdapterPosition(view)
            )
        })
    private lateinit var topics: Spinner

    private val dialogCallback: () -> Unit = {
        presenter.updateNewsfeed((topics.selectedItem as TextView).text.toString())
        dialog.dismiss()
    }
    private val dialog =
        DialogFactory.buildErrorDialog(R.string.msg_err, R.string.btn_positive, dialogCallback)

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
        val manager = supportFragmentManager
        dialog.show(manager, DIALOG_TAG)
    }

    override fun openEntry(activity: Class<*>, article: NewsContract.Article) {
        val showArticle = Intent(this, activity)
        showArticle.putExtra(NewsContract.KEY_EXTRA_ARTICLE, article)
        startActivity(showArticle)
    }
}
