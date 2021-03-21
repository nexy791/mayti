package com.ribsky.mayti.ui.adapters.search

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.chip.Chip
import com.ribsky.mayti.R
import com.ribsky.mayti.databinding.ItemSpotBinding
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.ui.activity.main.MainActivity
import com.ribsky.mayti.ui.fragment.search.SearchFragment
import com.ribsky.mayti.util.AlertsUtil
import com.ribsky.mayti.util.ExtraUtil

class CardStackAdapter(
    private var spots: List<UserModel> = emptyList(),
    private var fragment: SearchFragment
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_spot, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        with(holder.binding) {
            textView.text = spot.fln
            textView2.text = spot.bio

            imageView.load(spot.photo) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_playstore)
                transformations(CircleCropTransformation())
            }



            send.setOnClickListener {
                if ((fragment.requireActivity() as MainActivity).currentCoin > 0
                ) {
                    (fragment.requireActivity() as MainActivity).currentCoin--
                    ExtraUtil().setLikes(
                        root.context,
                        (fragment.requireActivity() as MainActivity).currentAccount.uid,
                        (fragment.requireActivity() as MainActivity).currentCoin
                    )

                    root.context.startActivity(
                        Intent.createChooser(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(spot.social)
                            ), "Написать"
                        )
                    )

                    (fragment.requireActivity() as MainActivity).updateBadger()

                } else {

                    AlertsUtil(fragment.requireActivity()).alertNoLikes()

                }

            }

            report.setOnClickListener {
                AlertsUtil(fragment.requireActivity()).alertSupport()
            }

            close.setOnClickListener {
                fragment.swipeClose()
            }

            chips.removeAllViews()
            for (i in spot.games) {
                if (i >= 0) {
                    chips.addView(Chip(root.context).apply {
                        text = ExtraUtil.LIST_OF_GAMES[i]
                    })
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    fun setSpots(spots: List<UserModel>) {
        this.spots = spots
    }

    fun getSpots(): List<UserModel> {
        return spots
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSpotBinding.bind(view)
    }

}