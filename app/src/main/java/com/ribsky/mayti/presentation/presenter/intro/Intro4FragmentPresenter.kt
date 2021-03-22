package com.ribsky.mayti.presentation.presenter.intro

import com.ribsky.mayti.presentation.view.intro.Intro4Contract

class Intro4FragmentPresenter(val mView: Intro4Contract.View) : Intro4Contract.Presenter {

    override fun buttonNextClicked(social: String) {
        if (social.isNotBlank() && social.length > 2 && social.length < 20) {
            mView.writeUser(social)
            mView.goFragment()
        } else {
            mView.showError("Необходимо указать ВК")
        }
    }
}