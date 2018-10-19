package prabhu.chandran.wikitest

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.SearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var client : WikiAPI? = null
    private var adapter : ResultAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        client = WikiClient.instance.create(WikiAPI::class.java)
    }

    override fun onStart() {
        super.onStart()
        adapter = ResultAdapter()
        searcher.setOnQueryTextListener(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter!!
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!!.isNotEmpty()) {
            searchQuery(newText)
            return true
        }
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()){
            searchQuery(query)
            return true
        }
        return false
    }
    @SuppressLint("CheckResult")
    fun searchQuery(query : Any?){
        client!!.search(gpssearch=query)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it!!.batchcomplete!!) {
                        adapter!!.loadData(it.query!!.pages!!)
                    }
                },{
                    Log.e("HomePage","onError:\t${it!!.localizedMessage}")
                })
    }
}
