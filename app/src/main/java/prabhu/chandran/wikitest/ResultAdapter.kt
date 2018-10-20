package prabhu.chandran.wikitest

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import prabhu.chandran.wikitest.databinding.SearchResultHolderBinding

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultHolder>() {
    private var inflater : LayoutInflater? = null
    private var dataItems : List<Pages>? = listOf()
    private var listener : OnResult? = null

    fun loadData(dataItems : List<Pages>?){
        this.dataItems = dataItems
        notifyDataSetChanged()
    }

    fun onListener(listener : OnResult?){
        this.listener = listener!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        if (inflater == null){
            inflater = LayoutInflater.from(parent.context)
        }

        val binding : SearchResultHolderBinding = DataBindingUtil.inflate(inflater!!, prabhu.chandran.wikitest.R.layout.search_result_holder,parent,false)
        return ResultHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return dataItems!!.size
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.bind.data = dataItems!![position]
        holder.bind.resCard.setOnClickListener {
            if (listener != null){
                listener!!.onResult(dataItems!![position].pageid!!,dataItems!![position].title.toString().replace(" ","_"))
            }
        }
    }

    class ResultHolder(binding : SearchResultHolderBinding) : RecyclerView.ViewHolder(binding.root) {
         val bind: SearchResultHolderBinding = binding
    }

    interface OnResult{
        fun onResult(pageId : Int?,title : Any?)
    }
}