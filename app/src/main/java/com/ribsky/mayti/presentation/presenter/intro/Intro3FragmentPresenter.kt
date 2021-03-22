package com.ribsky.mayti.presentation.presenter.intro

import com.ribsky.mayti.presentation.view.intro.Intro3Contract

class Intro3FragmentPresenter(val mView: Intro3Contract.View) : Intro3Contract.Presenter {

    override fun buttonNextClicked(bio: String) {

        if (bio.isNotBlank() && bio.length > 5) {

            if (bio.length <= 150) {
                mView.writeUser(bio)
                mView.goFragment()
            } else {
                mView.showError("Слишком много символов")
            }

        } else {
            mView.showError("Слишком мало символов")
        }


    }
}