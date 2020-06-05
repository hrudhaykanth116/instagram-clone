package com.hrudhaykanth116.instagramclone.helpers

class UserPostsDataGenerator {

    /*var captionId: Int = 1001

    val usersList = arrayListOf<UserPost.User>(
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(45), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(65), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(85), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116"),
        UserPost.User("Hrudhay kanth", "116", getProfilePictureUrl(25), "hrudhaykanth116")
    )

    val captionsList = arrayListOf<String>(

    )

    fun generateJson(){
        val user = generateUser()
        val caption = generateCaption(user)
        val images = generateImages()
        UserPost(
            1,
            caption,
            System.currentTimeMillis().toString(),
            "",
            UUID.randomUUID().toString(),
            images,
            "link",
            "image",
            user,
            false
        )
    }

    private fun getProfilePictureUrl(id: Int): String{
        return "https://picsum.photos/id/$id/300"
    }

    private fun generateUser(): UserPost.User{
        val randomNum: Int = Random.nextInt(0, usersList.size)
        return usersList[randomNum]
    }

    private fun generateImages() = UserPost.Images()

    private fun generateCaption(user: UserPost.User): UserPost.Caption{
        return UserPost.Caption(getRandomTime().toString(), user, captionId++.toString(), getCaptionText())
    }

    private fun getCaptionText(): String? {


    }

    private fun getRandomTime(): Long {
        // time between 2020/01/01 and current time
        return System.currentTimeMillis() - Random.nextLong(1577817000000, System.currentTimeMillis())
    }*/

}