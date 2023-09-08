
data class UserProfile(
    var firstName: String,
    var lastName: String,
    var email: String,
    var summary: String,
    var followers: String,
    var following: String,
    var profileImageRes: Int
) {
    fun copyFrom(other: UserProfile) {
        firstName = other.firstName
        lastName = other.lastName
        email = other.email
        summary = other.summary
        followers = other.followers
        following = other.following
        profileImageRes = other.profileImageRes
    }
}



