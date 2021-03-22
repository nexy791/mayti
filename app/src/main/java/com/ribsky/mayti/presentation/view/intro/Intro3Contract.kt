package com.ribsky.mayti.presentation.view.intro

class Intro3Contract {

    interface View {
        fun showError(text: String)
        fun goFragment()
        fun writeUser(bio: String)
    }

    interface Presenter {
        fun buttonNextClicked(bio: String)
    }
}