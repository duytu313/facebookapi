package com.example.facebook

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

// ================== MODELS ==================

// Dữ liệu post server trả về
data class PostData(
    val _id: String?,       // ID server
    val userId: String?,
    val userName: String?,  // thêm userName nếu server trả
    val content: String?,
    val image: String?,     // URL ảnh từ server
    val videoId: String?,   // ID YouTube nếu server trả
    val createdAt: String?,
    val isLiked: Boolean? = false
)

// Response khi tạo bài viết
data class PostResponse(
    val success: Boolean,
    val message: String,
    val post: PostData?
)

// Response khi lấy danh sách bài viết
data class PostsResponse(
    val success: Boolean,
    val posts: List<PostData>
)

// Response like
data class LikeResponse(
    val success: Boolean,
    val message: String
)

// Response comment
data class CommentResponse(
    val success: Boolean,
    val message: String,
    val comment: Comment?
)

// Response comment list
data class CommentsResponse(
    val success: Boolean,
    val comments: List<Comment>
)

// Auth response
data class AuthResponse(
    val success: Boolean,
    val message: String,
    val userId: String?,
    val token: String?
)

// ================== API SERVICE ==================
interface ApiService {

    // ======= Đăng nhập =======
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("usernameOrEmail") usernameOrEmail: String,
        @Field("password") password: String
    ): Call<AuthResponse>

    // ======= Đăng ký =======
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): Call<AuthResponse>

    // ======= Tạo bài viết =======
    @Multipart
    @POST("createPost")
    fun createPost(
        @Part("userId") userId: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): Call<PostResponse>

    // ======= Lấy danh sách bài viết =======
    @GET("posts")
    fun getPosts(): Call<PostsResponse>

    // ======= Like bài viết =======
    @FormUrlEncoded
    @POST("likePost")
    fun likePost(
        @Field("postId") postId: String,
        @Field("userId") userId: String
    ): Call<LikeResponse>

    // ======= Comment bài viết =======
    @FormUrlEncoded
    @POST("commentPost")
    fun commentPost(
        @Field("postId") postId: String,
        @Field("userId") userId: String,
        @Field("comment") comment: String
    ): Call<CommentResponse>

    // ======= Lấy comment của 1 bài viết =======
    @GET("comments/{postId}")
    fun getComments(
        @Path("postId") postId: String
    ): Call<CommentsResponse>
}
