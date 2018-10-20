package prabhu.chandran.wikitest

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity(), SearchView.OnQueryTextListener, ResultAdapter.OnResult {

    private var client : WikiAPI? = null
    private var adapter : ResultAdapter? = null
    private var model :ResponseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        client = WikiClient.instance.create(WikiAPI::class.java)
        model = ViewModelProviders.of(this).get(ResponseModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        adapter = ResultAdapter()
        adapter!!.onListener(this)
        searcher.setOnQueryTextListener(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter!!
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!!.isNotEmpty()) {
            searchQuery(newText)
            return true
        } else {
            adapter!!.loadData(listOf())
            noResult.visibility = View.VISIBLE
        }
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!!.isNotEmpty()){
            searchQuery(query)
            return true
        } else {
            adapter!!.loadData(listOf())
            noResult.visibility = View.VISIBLE
        }
        return false
    }

    private fun searchQuery(query : Any?){
        model!!.getResult(query)
            .observeForever {
                if (it!!.batchcomplete!!){
                    noResult.visibility = View.GONE
                    adapter!!.loadData(it.query!!.pages!!)
                } else {
                    noResult.visibility = View.VISIBLE
                }
            }
    }

    override fun onResult(pageId: Int?,title : Any?) {
        startActivity(Intent(this,WebViewPage::class.java)
            .putExtra("title","${title!!}"))
    }

    override fun onResume() {
        super.onResume()
        if (searcher.query!!.isNotEmpty()) {
            searchQuery(searcher!!.query!!)
        }
    }
}
