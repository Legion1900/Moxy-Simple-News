package com.legion1900.moxynews.model

import com.legion1900.moxynews.contracts.NewsContract
import com.legion1900.moxynews.models.repo.CachingNewsRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CachingNewsRepositoryTest {

    @Mock
    lateinit var onStart: () -> Unit
    @Mock
    lateinit var onLoaded: (NewsContract.Response) -> Unit
    @Mock
    lateinit var onFailure: () -> Unit
    @Mock
    lateinit var mockResponse: NewsContract.Response

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var repo: CachingNewsRepository

    @Before
    fun onSetup() {
        repo = CachingNewsRepository(onStart, onLoaded, onFailure)
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
}