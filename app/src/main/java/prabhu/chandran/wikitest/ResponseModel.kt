package prabhu.chandran.wikitest

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResponseModel : ViewModel() {
    private val result : MutableLiveData<Response>? = MutableLiveData()
    fun getResult(query : Any?) : LiveData<Response>{
        loadResult(query!!)
        return result!!
    }

    @SuppressLint("CheckResult")
    private fun loadResult(query: Any) {
        val client =  WikiClient.instance.create(WikiAPI::class.java)
        client.search(gpssearch = query)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result!!.postValue(it!!)
            },{
                Log.e("RESP_MODEL","onError:\t${it!!.localizedMessage!!}")
            })
    }
}