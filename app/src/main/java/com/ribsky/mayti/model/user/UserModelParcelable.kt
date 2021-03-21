package com.ribsky.mayti.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModelParcelable(
    var uid: String,
    var fln: String,
    var bio: String,
    var photo: String,
    var games: List<Int>,
    var social: String,
    var isBlocked: Boolean
) : Parcelable {
    fun fromParcelable() = UserModel(
        uid = uid,
        fln = fln,
        bio = bio,
        photo = photo,
        games = games,
        social = social,
        isBlocked = isBlocked
    )
}

