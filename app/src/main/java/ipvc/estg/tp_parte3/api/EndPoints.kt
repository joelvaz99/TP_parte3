package ipvc.estg.tp_parte3.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("/users/")
    fun getUsers(): Call<List<User>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("/myslim/api/userlogin/")
    fun postTest(
        @Field("username") username: String?,
        @Field("password") password: String?): Call<OutputPost>
}