package com.franco.mytestapplication.common

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 23/12/21 at 11:08 p.m.
 */
abstract class BaseViewModel: ViewModel() {

    private val _loadingStateObservable: MutableLiveData<Boolean> = MutableLiveData()
    val loadingStateObservable: LiveData<Boolean> get() = _loadingStateObservable

    @MainThread
    protected fun setLoading(loading: Boolean) {
        _loadingStateObservable.value = loading
    }

}