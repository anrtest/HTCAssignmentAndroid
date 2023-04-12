package com.example.assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.api.response.bycity.ResponseByCityNameItem
import com.example.assignment.databinding.ItemRowBinding


class CityAdapter(val context: Context, val listner: CustomClickListener?): RecyclerView.Adapter<CityAdapter.ViewHolder>()  {
    private var responseByCityNameItemList: List<ResponseByCityNameItem>? = null

    fun setData(responseByCityNameItemList: List<ResponseByCityNameItem>?) {
        this.responseByCityNameItemList =responseByCityNameItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_row, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val responseByCityNameItem: ResponseByCityNameItem = this.responseByCityNameItemList!![position]
        holder.bind(responseByCityNameItem) {
            listner?.cardClicked(responseByCityNameItem)
        }
    }


    override fun getItemCount(): Int {
        return this.responseByCityNameItemList?.size ?: 0
    }

    class ViewHolder(itemRowBinding: ItemRowBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        var itemRowBinding: ItemRowBinding = itemRowBinding
        fun bind(obj: ResponseByCityNameItem?, listner: ()-> Unit) {
            itemRowBinding.model = obj
            itemRowBinding.executePendingBindings()
            itemRowBinding.root.setOnClickListener {
                listner()
            }
        }


    }


}