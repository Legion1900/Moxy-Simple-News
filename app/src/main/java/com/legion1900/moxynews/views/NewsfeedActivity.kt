package com.legion1900.moxynews.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.legion1900.moxynews.BuildConfig
import com.legion1900.moxynews.R
import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.presenters.NewsPresenter
import com.legion1900.moxynews.utils.dialogfactory.ErrorDialogFactory
import com.legion1900.moxynews.views.adapters.ArticleAdapter
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class NewsfeedActivity : MvpAppCompatActivity(), NewsContract.NewsfeedView {

    companion object {
        const val DIALOG_TAG = BuildConfig.APPLICATION_ID
    }

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    private val dialogFragment: DialogFragment =
        ErrorDialogFactory.createErrorDialog(R.string.msg_err, R.string.btn_positive, object :
            ErrorDialogFactory.PositiveCallback {
            override fun onPositive() {
                requestNews()
            }
        })

    private lateinit var topics: Spinner
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ArticleAdapter
    private val rvClickListener = View.OnClickListener { view ->
        presenter.onArticleClick(
            rv.getChildAdapterPosition(view)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newsfeed)

        initRecyclerView()
        initSwipeRefresh()
        initSpinner()
    }

    private fun initRecyclerView() {
        rv = findViewById(R.id.rv_news)
        rv.setHasFixedSize(true)
        adapter = ArticleAdapter(rvClickListener, this)
        rv.adapter = adapter
    }

    private fun initSwipeRefresh() {
        swipeRefresh = findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            requestNews()
        }
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
                requestNews()
            }
        }
    }

    private fun requestNews() {
        val topic = topics.selectedItem as String
        presenter.updateNewsfeed(topic)
    }

    override fun displayNewsfeed(articles: List<NewsContract.Article>) {
        adapter.updateData(articles)
    }

    override fun displayErrorDialog() {
        val manager = supportFragmentManager
        dialogFragment.show(manager, DIALOG_TAG)
    }

    override fun setLoadingAnimation(isLoading: Boolean) {
        swipeRefresh.isRefreshing = isLoading
    }

    override fun openEntry(activity: Class<*>) {
        val showArticle = Intent(this, activity)
        startActivity(showArticle)
    }
}
