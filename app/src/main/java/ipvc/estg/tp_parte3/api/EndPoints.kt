package ipvc.estg.tp_parte3.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("/myslim/api/pontos")
    fun getPontos(): Call<List<User>>

    @GET("/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("/myslim/api/userlogin/")
    fun postTest(
        @Field("username") username: String?,
        @Field("password") password: String?): Call<OutputPost>

    @FormUrlEncoded
    @POST("/myslim/api/addproblem/")
    fun postProblem(
        @Field("latitude") latitude: String?,
        @Field("longitude") longitude: String?,
        @Field("descricao") descricao: String?,
        @Field("foto") foto: String?): Call<OutputPost>

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part part: MultipartBody.Part?,
        @Part("somedata") requestBody: RequestBody?
    ): Call<RequestBody?>?
}