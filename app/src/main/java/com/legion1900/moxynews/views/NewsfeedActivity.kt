package com.legion1900.moxynews.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.legion1900.moxynews.BuildConfig
import com.legion1900.moxynews.R
import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.presenters.NewsPresenter
import com.legion1900.moxynews.utils.ErrorDialog
import com.legion1900.moxynews.views.adapters.ArticleAdapter
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class NewsfeedActivity : MvpAppCompatActivity(), NewsContract.NewsfeedView {

//    TODO: implement swipe-refresh layout

    companion object {
        const val DIALOG_TAG = BuildConfig.APPLICATION_ID
    }

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    private lateinit var dialogFragment: DialogFragment

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
        initErrorDialog()
    }

    private fun initRecyclerView() {
        rv = findViewById(R.id.rv_news)
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }

    private fun initSpinner() {
        topics = findViewById(R.id.spinner_topics)
        topics.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                /*
                * Nothing to do here.
                * */
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val topic = topics.selectedItem as String
                presenter.updateNewsfeed(topic)
            }
        }
    }

    private fun initErrorDialog() {
        dialogFragment = ErrorDialog()
        val args = Bundle()
        args.putInt(ErrorDialog.KEY_MSG, R.string.msg_err)
        args.putInt(ErrorDialog.KEY_TXT, R.string.btn_positive)
        args.putParcelable(ErrorDialog.KEY_CALLBACK, object : ErrorDialog.PositiveCallback {
            override fun onPositive() {
                presenter.updateNewsfeed(topics.selectedItem as String)
            }
        })
        dialogFragment.arguments = args
    }

    override fun displayNewsfeed(articles: List<NewsContract.Article>) {
        adapter.updateData(articles)
    }

    override fun displayErrorDialog() {
        val manager = supportFragmentManager
        dialogFragment.show(manager, DIALOG_TAG)
    }

    override fun openEntry(activity: Class<*>, article: NewsContract.Article) {
        val showArticle = Intent(this, activity)
        showArticle.putExtra(NewsContract.KEY_EXTRA_ARTICLE, article)
        startActivity(showArticle)
    }
}
