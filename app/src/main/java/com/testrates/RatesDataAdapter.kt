package com.testrates


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.testrates.bind.BinderAdapterComponent
import com.testrates.data.model.RateItemObject
import com.testrates.databinding.RateItemBinding
import com.testrates.utils.MyDiffUtilCallBack


class RatesDataAdapter(
    rateList: ArrayList<RateItemObject>, private val binderComponent: BinderAdapterComponent,
    adapterListener: RateDataAdapterListener
) :
    RecyclerView.Adapter<RatesDataAdapter.RateViewHolder>() {

    private var rateItemList = rateList
    val mListener = adapterListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RateViewHolder {
        val binding = DataBindingUtil.inflate<RateItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rate_item, parent, false, binderComponent
        )
        return RateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rateItemList.size
    }

    fun swapItem(fromPosition: Int, itemdata: RateItemObject) {
        rateItemList.removeAt(fromPosition)
        notifyItemRemoved(fromPosition)
        notifyItemChanged(fromPosition)
        rateItemList.add(0, itemdata)
        notifyItemInserted(0)
        notifyItemChanged(0)
    }

    fun updateList(item: ArrayList<RateItemObject>) {
        rateItemList = item
        notifyDataSetChanged()
       /* val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallBack(item, rateItemList))
        diffResult.dispatchUpdatesTo(this)
        this.rateItemList.clear()
        this.rateItemList.addAll(item)*/
    }


    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bindItems(rateItemList[position])
    }

    inner class RateViewHolder(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: RateItemObject) {
            binding.rateInfo = item
            binding.root.setOnClickListener {
                val pos = adapterPosition
                mListener.currentItemPosition(pos, rateItemList[pos])

            }
        }
    }

    override fun onBindViewHolder(
        holder: RateViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val o = payloads[0] as Bundle
            for (key in o.keySet()) {
                if (key == "price") {
                    holder.bindItems(
                        RateItemObject(
                            rateItemList[position].countryImage,
                            rateItemList[position].countryName,
                            rateItemList[position].countryCurrencyName,
                            rateItemList[position].countryRate
                        )
                    )
                }
            }
        }

    }

    interface RateDataAdapterListener {
        fun currentItemPosition(currentPosition: Int, itemData: RateItemObject)
    }
}