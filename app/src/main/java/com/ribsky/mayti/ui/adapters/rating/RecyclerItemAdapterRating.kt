package com.ribsky.mayti.ui.adapters.rating

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ItemRatingBinding
import com.ribsky.mayti.model.user.UserModel

class RecyclerItemAdapterRating(modelList: ArrayList<UserModel>) :

    RecyclerView.Adapter<RecyclerItemAdapterRating.UsersSheetHolder>() {
    private val mModelList: List<UserModel> = modelList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersSheetHolder {
        return UsersSheetHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rating, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UsersSheetHolder, position: Int) {
        val model: UserModel = mModelList[position]
        with(holder) {
            binding.title.text = model.fln
            binding.imageView2.load(model.photo) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_playstore)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemCount(): Int {
        return mModelList.size
    }

    inner class UsersSheetHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ItemRatingBinding.bind(itemView)
    }

}