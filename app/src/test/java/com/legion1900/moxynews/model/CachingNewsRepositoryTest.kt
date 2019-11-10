package com.legion1900.moxynews.model

import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.models.repo.CachingNewsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*

class CachingNewsRepositoryTest {

    private lateinit var onStart: () -> Unit
    private lateinit var onLoaded: (NewsContract.Response) -> Unit
    private lateinit var onFailure: () -> Unit
    @Mock
    lateinit var mockResponse: NewsContract.Response

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var repo: CachingNewsRepository

    private val topic0: String
    private val date0: Date
    private val topic1: String
    private val date1: Date

    init {
        val calendar = Calendar.getInstance()
        date0 = calendar.time
        calendar.add(Calendar.MILLISECOND, CachingNewsRepository.TIMEOUT)
        date1 = calendar.time
        topic0 = "sport"
        topic1 = "cinema"
    }

    @Before
    fun onSetup() {
        onStart = mock()
        onLoaded = mock()
        onFailure = mock()
        repo = CachingNewsRepository(onStart, onLoaded, onFailure)

        repo.loadNews(topic0, date0)
    }

    @Test
    fun onLoadedCallback_nullResponseTest() {
        repo.onLoadedCallback(null)
        verify(onFailure).invoke()
    }

    @Test
    fun onLoadedCallback_nonNullTest() {
        repo.onLoadedCallback(mockResponse)
        verify(onLoaded).invoke(mockResponse)
    }

    @Test
    fun `loadNews should not call onStartCallback when same topic or not enough time has passed`() {
        repo.loadNews(topic0, date0)
        verifyLoads(1)
    }

    @Test
    fun `loadNews should call onStartCallback when same topic & enough time has passed`() {
        repo.loadNews(topic0, date1)
        verifyLoads(2)
    }

    @Test
    fun `loadNews should call onStartCallback when different topic & not enough time has passed`() {
        repo.loadNews(topic1, date0)
        verifyLoads(2)
    }

    @Test
    fun `loadNes should call onStartCallback when different topic & enough time has passed`() {
        repo.loadNews(topic1, date1)
        verifyLoads(2)
    }

    private fun verifyLoads(times: Int) {
        verify(onStart, times(times)).invoke()
    }
}