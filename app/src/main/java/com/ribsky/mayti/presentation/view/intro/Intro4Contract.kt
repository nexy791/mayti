package com.ribsky.mayti.presentation.view.intro

class Intro4Contract {

    interface View {
        fun showError(text: String)
        fun goFragment()
        fun writeUser(social: String)
    }

    interface Presenter {
        fun buttonNextClicked(social: String)
    }
}