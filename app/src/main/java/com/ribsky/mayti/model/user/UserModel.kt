package com.ribsky.mayti.model.user

import com.google.firebase.database.Exclude

class UserModel(
    var uid: String,
    var fln: String,
    var bio: String,
    var photo: String,
    var games: List<Int>,
    var social: String,
    var isBlocked: Boolean
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "fln" to fln,
            "bio" to bio,
            "photo" to photo,
            "games" to games,
            "social" to social,
            "isBlocked" to isBlocked
        )
    }

    fun toParcelable() = UserModelParcelable(
        uid = uid,
        fln = fln,
        bio = bio,
        photo = photo,
        games = games,
        social = social,
        isBlocked = isBlocked
    )

}
