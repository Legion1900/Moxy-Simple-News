package com.legion1900.moxynews.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.legion1900.moxynews.BuildConfig
import com.legion1900.moxynews.R
import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.presenters.NewsPresenter
import com.legion1900.moxynews.utils.DialogFactory
import com.legion1900.moxynews.views.adapters.ArticleAdapter
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class NewsfeedActivity : MvpAppCompatActivity(), NewsContract.NewsfeedView {

//    TODO: implement swipe-refresh layout

    companion object {
        const val DIALOG_TAG = BuildConfig.APPLICATION_ID
    }

    //    TODO: this one does not inject presenters (at least in kotlin)
    @InjectPresenter
    internal lateinit var presenter: NewsPresenter

    private val dialogCallback: () -> Unit = {
        presenter.updateNewsfeed((topics.selectedItem as TextView).text.toString())
    }
    private val dialog =
        DialogFactory.buildErrorDialog(R.string.msg_err, R.string.btn_positive, dialogCallback)

    private lateinit var topics: Spinner
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
        initSpinner()
    }

    private fun initRecyclerView() {
        rv = findViewById(R.id.rv_news)
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }

    private fun initSpinner() {
        topics = findViewById(R.id.spinner_topics)
        topics.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            var falseTrigger = true

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /*
                * Nothing to do here.
                * */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!falseTrigger) {
                    val topic = (view as TextView).text
                    presenter.updateNewsfeed(topic.toString())
                } else
                    falseTrigger = false
            }

        }
    }

    override fun displayNewsfeed(articles: List<NewsContract.Article>) {
        adapter.updateData(articles)
    }

    override fun displayErrorDialog(visible: Boolean) {
        if (visible) {
            val manager = supportFragmentManager
            dialog.show(manager, DIALOG_TAG)
        } else if (dialog.showsDialog)
            dialog.dismiss()
    }

    override fun openEntry(activity: Class<*>, article: NewsContract.Article) {
        val showArticle = Intent(this, activity)
        showArticle.putExtra(NewsContract.KEY_EXTRA_ARTICLE, article)
        startActivity(showArticle)
    }
}
