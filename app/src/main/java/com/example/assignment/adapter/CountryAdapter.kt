package com.example.assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.api.response.country.CountryResponseItem
import com.example.assignment.databinding.ItemRowBinding

class CountryAdapter(val context: Context): RecyclerView.Adapter<CountryAdapter.ViewHolder>()  {
    private var countryResponseList: List<CountryResponseItem>? = null

    fun setData(countryResponseList: List<CountryResponseItem>?) {
        this.countryResponseList = countryResponseList
        notifyDataSetChanged()
    }
    fun getData(): List<CountryResponseItem>? {
        return this.countryResponseList
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
        val countryResponse: CountryResponseItem = this.countryResponseList!![position]
        holder.bind(countryResponse)
    }


    override fun getItemCount(): Int {
        return this.countryResponseList?.size ?: 0
    }

    class ViewHolder(itemRowBinding: ItemRowBinding) :
            RecyclerView.ViewHolder(itemRowBinding.root) {
        var itemRowBinding: ItemRowBinding = itemRowBinding
        fun bind(obj: CountryResponseItem?) {
            itemRowBinding.model = obj
            itemRowBinding.executePendingBindings()
            obj?.loadImage(itemRowBinding.imageView)

        }


    }


}