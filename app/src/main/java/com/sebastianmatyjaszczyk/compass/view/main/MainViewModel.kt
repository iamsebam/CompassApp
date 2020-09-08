package com.sebastianmatyjaszczyk.compass.view.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sebastianmatyjaszczyk.compass.repository.AzimuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainViewModel
@ViewModelInject constructor(
    private val azimuthRepository: AzimuthRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {
    val azimuthLiveData: MutableLiveData<Int> = MutableLiveData()

    private val disposableContainer = CompositeDisposable()

    init {
        disposableContainer.add(
            azimuthRepository.azimuth
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { azimuth ->
                    azimuthLiveData.value = azimuth
                })
    }

    override fun onCleared() {
        disposableContainer.clear()
        super.onCleared()
    }
}