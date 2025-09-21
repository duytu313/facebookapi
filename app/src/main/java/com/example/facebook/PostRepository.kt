package com.example.facebook

object PostRepository {
    private val posts = mutableListOf<Post>()

    fun addPost(post: Post) {
        posts.add(0, post) // Thêm bài mới lên đầu
    }

    fun getPosts(): MutableList<Post> = posts
}
