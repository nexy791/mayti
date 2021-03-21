package com.ribsky.mayti.ui.adapters.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ItemGameBinding
import com.ribsky.mayti.model.game.GameModel

class RecyclerViewAdapterGames(modelList: ArrayList<GameModel>) :

    RecyclerView.Adapter<RecyclerViewAdapterGames.GamesSheetHolder>() {
    private val mModelList: List<GameModel> = modelList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesSheetHolder {
        return GamesSheetHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GamesSheetHolder, position: Int) {
        val model: GameModel = mModelList[position]
        with(holder) {
            binding.title.text = model.name
        }
    }

    override fun getItemCount(): Int {
        return mModelList.size
    }

    inner class GamesSheetHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = ItemGameBinding.bind(itemView)
    }

}