package com.application.cryptomaniac.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.cryptomaniac.data.model.Crypto

class CryptoAdapter(val clickListener: CryptoClickListener) :
    RecyclerView.Adapter<CryptoHolder>() {

    private var data = listOf<Crypto?>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        return CryptoHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface CryptoClickListener {
        fun onItemClick(item: Crypto?)
    }
}



