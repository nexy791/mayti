package com.ribsky.mayti.ui.adapters.lib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ItemLibBinding
import com.ribsky.mayti.model.lib.LibModel

class RecyclerViewAdapterLib(modelList: ArrayList<LibModel>) :

    RecyclerView.Adapter<RecyclerViewAdapterLib.LibSheetHolder>() {
    private val mModelList: List<LibModel> = modelList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibSheetHolder {
        return LibSheetHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lib, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LibSheetHolder, position: Int) {
        val model: LibModel = mModelList[position]
        with(holder) {
            binding.textViewName.text = model.name
            binding.textViewDesc.text = model.text
        }
    }

    override fun getItemCount(): Int {
        return mModelList.size
    }

    inner class LibSheetHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ItemLibBinding.bind(itemView)
    }

}