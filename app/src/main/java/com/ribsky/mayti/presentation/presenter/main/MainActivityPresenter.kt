package com.ribsky.mayti.presentation.presenter.main

import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.main.MainActivityContract
import com.ribsky.mayti.repository.main.MainRepository

class MainActivityPresenter(private val mView: MainActivityContract.View) :
    MainActivityContract.Presenter {

    private val mRepository: MainActivityContract.Repository = MainRepository()

    override fun onCreate() {
        mRepository.initialization {
            if (it) {

                val users: ArrayList<UserModel> = mRepository.getUsers()
                var user: UserModel? = null

                for (i in users) {
                    if (i.uid == mRepository.getAuthUser()!!.uid) {
                        user = i
                        mRepository.setCurrentUser(user)
                    }
                }

                if (user != null) {
                    if (user.social.isNotBlank()) {

                        if (!user.isBlocked) {
                            mView.startApp(users)
                        } else {
                            mView.userBlocked()
                        }

                    } else mView.criticalErrorAuth()
                } else mView.criticalErrorAuth()
            } else mView.criticalError()
        }
    }

    override fun getCurrentUser(): UserModel {
        return mRepository.getCurrentUser()
    }

    override fun setCurrentUser(userModel: UserModel) {
        mRepository.setCurrentUser(userModel)
    }

    override fun getUsers(): ArrayList<UserModel> {
        return mRepository.getUsers()
    }
}