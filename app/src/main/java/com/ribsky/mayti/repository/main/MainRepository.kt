package com.ribsky.mayti.repository.main

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ribsky.mayti.model.user.UserModel
import com.ribsky.mayti.presentation.view.main.MainContract
import com.ribsky.mayti.util.ExtraUtil


class MainRepository : MainContract.Repository {

    private val database: FirebaseDatabase =
        Firebase.database(ExtraUtil.FIREBASE_DATABASE_ADDRESS)
    private val databaseReference: DatabaseReference = database.reference
    private lateinit var dataSnapshot: DataSnapshot

    override fun getAuthUser(): FirebaseUser? {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.currentUser
    }

    override fun initialization(callback: (result: Boolean) -> Unit) {
        // database.setPersistenceEnabled(true)
        databaseReference.get().addOnSuccessListener {
            dataSnapshot = it
            callback.invoke(true)
        }.addOnFailureListener {
            callback.invoke(false)
        }
    }

    override fun getUsers(): ArrayList<UserModel> {
        val arrayList: ArrayList<UserModel> = ArrayList()
        dataSnapshot.children.forEach {
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
        return arrayList
    }


}