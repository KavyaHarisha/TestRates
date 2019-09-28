package com.testrates.viewmodel

import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.testrates.data.model.RateItemObject
import com.testrates.data.repository.RateResponseRepository
import com.testrates.utils.CURRENCY_FLAGS
import com.testrates.utils.CURRENCY_LONG_NAMES
import com.testrates.utils.CURRENCY_NAMES
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RateResponseViewModel @Inject
constructor(private val repoRepository: RateResponseRepository) : ViewModel() {

    private var disposable: CompositeDisposable? = null
    private val repoLoadError = MutableLiveData<Boolean>()
    private var ratesHashMap = ObservableArrayMap<String, Double>()
    var rateItemList = ArrayList<RateItemObject>()
    var liveRateDate = MutableLiveData<List<RateItemObject>>()
    var currentValue = 1.0
    var convertValue: Double? = null
    var updateList = MutableLiveData<List<RateItemObject>>()

    init {
        disposable = CompositeDisposable()
        disposable?.add(
            Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fetchRates, this::getError)
        )
    }

    fun getRatesList(): LiveData<List<RateItemObject>> {
        return liveRateDate
    }

    fun getUpdateRatesList(): LiveData<List<RateItemObject>> {
        return updateList
    }

    fun getError(): LiveData<Boolean> {
        return repoLoadError
    }

    private fun fetchRates(long: Long) {
        disposable?.add(
            repoRepository.getRateObserverResponse("EUR")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::getError)
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }

    fun getCurrentRateChangeValue(toDouble: Double) {
        currentValue = toDouble
        rateItemList.forEach { c ->
            c.countryRate = (currentValue / convertValue!!) * ratesHashMap[c.countryName]!!
        }
        updateList.value = rateItemList
    }

    private fun handleResponse(cryptoList: Response<JsonObject>) {
        val list = ArrayList<RateItemObject>()
        try {
            val jsonString = cryptoList.body()
            val jObject = JSONObject(jsonString.toString()).getJSONObject("rates")
            val keys = jObject.keys()
            keys.forEach { ratesHashMap[it] = jObject.getDouble(it) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val symbols = CURRENCY_FLAGS
        val names = CURRENCY_NAMES
        val currencyNames = CURRENCY_LONG_NAMES


        convertValue = ratesHashMap["AUD"]
        rateItemList.clear()
        ratesHashMap.forEach { (key, value) ->
            run {
                for (name in 0..names.size) {
                    if (names[name] == key) {
                        list.add(
                            RateItemObject(
                                symbols[name], key,
                                currencyNames[name], value
                            )
                        )
                        break
                    }
                }
            }
        }
        rateItemList = list
        liveRateDate.value = list
    }

    private fun getError(errorThrow: Throwable) {
        repoLoadError.value = true
    }


}
