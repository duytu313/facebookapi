package com.example.facebook

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // ================= ĐĂNG NHẬP =================
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("usernameOrEmail") usernameOrEmail: String,
        @Field("password") password: String
    ): Call<AuthResponse>

    // ================= ĐĂNG KÝ =================
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): Call<AuthResponse>

    // ================= TẠO BÀI VIẾT =================
    @Multipart
    @POST("createPost")
    fun createPost(
        @Part("userId") userId: okhttp3.RequestBody,
        @Part("content") content: okhttp3.RequestBody,
        @Part image: okhttp3.MultipartBody.Part? = null
    ): Call<PostResponse>

    // ================= LẤY BÀI VIẾT =================
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    // ================= LIKE BÀI VIẾT =================
    @FormUrlEncoded
    @POST("likePost")
    fun likePost(
        @Field("postId") postId: String,
        @Field("userId") userId: String
    ): Call<LikeResponse>

    // ================= COMMENT BÀI VIẾT =================
    @FormUrlEncoded
    @POST("commentPost")
    fun commentPost(
        @Field("postId") postId: String,
        @Field("userId") userId: String,
        @Field("comment") comment: String
    ): Call<CommentResponse>

    // ================= LẤY COMMENT =================
    @GET("comments/{postId}")
    fun getComments(
        @Path("postId") postId: String
    ): Call<List<Comment>>
}
