package com.ribsky.mayti.repository.main

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.main.MainActivityContract
import com.ribsky.mayti.util.ExtraUtil


class MainRepository : MainActivityContract.Repository {

    private val database: FirebaseDatabase =
        Firebase.database(ExtraUtil.FIREBASE_DATABASE_ADDRESS)
    private val databaseReference: DatabaseReference = database.reference
    private lateinit var dataSnapshot: DataSnapshot

    lateinit var currentAccount: UserModel

    override fun getAuthUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override fun initialization(callback: (result: Boolean) -> Unit) {
        databaseReference.get().addOnSuccessListener {
            dataSnapshot = it
            callback.invoke(true)
        }.addOnFailureListener {
            callback.invoke(false)
        }
    }

    override fun getCurrentUser(): UserModel {
        return currentAccount
    }

    override fun setCurrentUser(userModel: UserModel) {
        currentAccount = userModel
        val database: FirebaseDatabase =
            Firebase.database(ExtraUtil.FIREBASE_DATABASE_ADDRESS)
        database.reference.root.child(userModel.uid).setValue(userModel.toMap())
    }

    override fun getUsers(): ArrayList<UserModel> {
        val arrayList: ArrayList<UserModel> = ArrayList()
        dataSnapshot.children.forEach {

            if (!(it.child("isBlocked").value as Boolean) && (it.child("social").value as String).isNotBlank()
            ) {
                arrayList.add(
                    UserModel(
                        it.child("uid").value as String,
                        it.child("fln").value as String,
                        it.child("bio").value as String,
                        it.child("photo").value as String,
                        (it.child("games").value as List<Long>).map { it1 -> it1.toInt() },
                        it.child("social").value as String,
                        it.child("isBlocked").value as Boolean
                    )
                )
            }
        }
        return arrayList
    }


}