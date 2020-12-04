package ipvc.estg.tp_parte3.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

    @GET("/myslim/api/pontos")
    fun getPontos(): Call<List<User>>

    @GET("myslim/api/obras/{type_id}")
    fun getPontoType(@Path("type_id") type_id: Int): Call<List<User>>

    @GET("myslim/api/ponto/{id}")
    fun getPontoid(@Path("id") id: Any?): Call<List<User>>

    @GET("myslim/api/pontodelete/{id}")
    fun deletePontoid(@Path("id") id: Any?): Call<OutputPost>

    @FormUrlEncoded
    @POST("myslim/api/pontoupdate/{id}")
    fun updatePonto(@Path("id") id: Any?,
                    @Field("descricao") descricao: String?,
                    @Field("type_id") type_id: String
    ): Call<OutputPost>



    @FormUrlEncoded
    @POST("/myslim/api/userlogin/")
    fun postTest(
        @Field("username") username: String?,
        @Field("password") password: String?): Call<OutputPost>


    @FormUrlEncoded
    @POST("/myslim/api/addponto1")
    fun postPonto1(
        @Field("descricao") descricao: String?,
        @Field("lat") lat: String?,
        @Field("longitude") longitude: String?,
        @Field("user_id") user_id:String?,
        @Field("type_id") type_id: String
    ): Call<OutputPost>

    @Multipart
    @POST("myslim/api/images/")
    fun upload(
        @Part file: MultipartBody.Part
    ): Call<String>


   

}