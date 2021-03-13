package com.ribsky.mayti.presenters

import com.ribsky.mayti.contracts.MainContract
import com.ribsky.mayti.model.UserModel
import com.ribsky.mayti.repositories.MainRepository

class MainPresenter(val mView: MainContract.View) : MainContract.Presenter {

    private val mRepository: MainContract.Repository = MainRepository()


    override fun onCreate() {
        var isOk = false
        mRepository.initialization {
            if (it) {

                val users: ArrayList<UserModel> = mRepository.getUsers()
                var user: UserModel? = null

                for (i in users) {
                    if (i.uid == mRepository.getAuthUser()!!.uid) {
                        user = i
                        isOk = true
                    }
                }

                if (isOk) {
                    if (user!!.social.isNotEmpty()) {

                        if (!user.isBlocked) {
                            mView.startApp(user, users)
                        } else {
                            mView.userBlocked()
                        }

                    } else {
                        mView.criticalErrorAuth()
                    }
                } else {
                    mView.criticalErrorAuth()
                }
            } else mView.criticalError()
        }
    }

}