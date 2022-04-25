package com.mohsenmb.mainmodule.ui.trendings

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mohsenmb.arch.data.Error
import com.mohsenmb.arch.data.ISchedulersProvider
import com.mohsenmb.arch.domain.Repo
import com.mohsenmb.arch.domain.RepositoryContract
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


class TrendingReposViewModel(
        private val repository: RepositoryContract.ITrendingReposRepository,
        private val schedulers: ISchedulersProvider
) : ViewModel() {
    private var page: Int = 1
    val errorsLiveData: LiveData<Error> by lazy {
        MutableLiveData<Error>()
    }
    val reposLiveData: LiveData<MutableList<Repo>> by lazy {
        MutableLiveData<MutableList<Repo>>()
    }

    fun loadTrendingRepos(reset: Boolean = false) {
        if (reset) {
            page = 1
        }
        repository
                .loadTrendingRepos(page)
                .observeOn(schedulers.ui())
                .subscribe({
                    (errorsLiveData as MutableLiveData<*>).value = Error.SUCCESS
                    (reposLiveData as MutableLiveData<*>).value = it.repos
                    page++
                }, { error ->
                    (errorsLiveData as MutableLiveData<*>).value = when (error) {
                        is HttpException -> {
                            if (error.code() == 422) {
                                Error.NO_MORE_DATA
                            } else {
                                Error.UNKNOWN
                            }
                        }
                        is SocketTimeoutException -> Error.TIMEOUT
                        is IOException -> Error.DISCONNECTED
                        else -> Error.UNKNOWN
                    }
                })
    }
}