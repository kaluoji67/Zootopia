package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.DaggerViewModelComponent

import com.example.myapplication.model.Animal
import com.example.myapplication.model.AnimalApiService
import com.example.myapplication.model.ApiKey
import com.example.myapplication.util.SharedPreferencesHelper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    @Inject
    lateinit var apiService : AnimalApiService

//    val prefs = SharedPreferencesHelper(getApplication())
    @Inject
    lateinit var prefs :SharedPreferencesHelper

    init{
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    private val disposable = CompositeDisposable()




    var invalidApi = false;

    fun refresh(){
        invalidApi = false;
        loading.value = true
        val key = prefs.getKey()
        if(key.isNullOrEmpty())
            getKey()
        else
            getAnimals(key)
    }

    private fun getKey(){
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<ApiKey>(){
                    override fun onSuccess(apiKey: ApiKey) {
                        if(apiKey.key.isNullOrEmpty()){
                            loading.value = false;
                            loadError.value= true
                        }else{
                            prefs.saveKey(apiKey.key)
                            getAnimals(apiKey.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false;
                        loadError.value= true
                    }

                })
        )
    }

    private fun getAnimals(key:String){
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(animalList: List<Animal>) {
                        loading.value = false;
                        loadError.value= false
                        animals.value = animalList
                    }

                    override fun onError(e: Throwable) {
                        if(!invalidApi){
                            invalidApi=true;
                            getKey()
                        }else {
                            e.printStackTrace()
                            loading.value = false;
                            loadError.value = true
                            animals.value = null
                        }
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}