package com.ribsky.mayti.repository.main

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.intro.IntroActivityContract
import com.ribsky.mayti.util.ExtraUtil

class IntroRepository : IntroActivityContract.Repository {

    lateinit var currentAccount: UserModel

    override fun getCurrentUser(): UserModel {
        return currentAccount
    }

    override fun setCurrentUser(userModel: UserModel) {
        currentAccount = userModel
        val database: FirebaseDatabase =
            Firebase.database(ExtraUtil.FIREBASE_DATABASE_ADDRESS)
        database.reference.root.child(userModel.uid).setValue(userModel.toMap())
    }
}