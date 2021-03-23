package com.ribsky.mayti.presentation.view.intro

class Intro2Contract {

    interface View {
        fun showError(text: String)
        fun goFragment()
        fun writeUser(checkIds: ArrayList<Int>)
    }

    interface Presenter {
        fun buttonNextClicked(checkIds: ArrayList<Int>)
    }
}