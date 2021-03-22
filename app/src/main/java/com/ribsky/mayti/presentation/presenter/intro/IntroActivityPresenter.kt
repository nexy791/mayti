package com.ribsky.mayti.presentation.presenter.intro

import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.intro.IntroActivityContract
import com.ribsky.mayti.repository.main.IntroRepository

class IntroActivityPresenter(val mView: IntroActivityContract.View) :
    IntroActivityContract.Presenter {

    private val mRepository: IntroActivityContract.Repository = IntroRepository()

    override fun getCurrentUser(): UserModel {
        return mRepository.getCurrentUser()
    }

    override fun setCurrentUser(userModel: UserModel) {
        mRepository.setCurrentUser(userModel)
    }
}