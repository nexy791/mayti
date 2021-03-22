package com.ribsky.mayti.presentation.presenter.intro

import com.ribsky.mayti.presentation.view.intro.Intro2Contract

class Intro2FragmentPresenter(private val mView: Intro2Contract.View) : Intro2Contract.Presenter {

    override fun buttonNextClicked(checkIds: ArrayList<Int>) {

        if (checkIds.isNotEmpty()) {
            mView.writeUser(checkIds)
            mView.goFragment()
        } else {
            mView.showError("Нужно выбрать игры")
        }


    }
}