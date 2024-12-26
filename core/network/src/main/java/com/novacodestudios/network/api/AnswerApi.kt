package com.novacodestudios.network.api

import com.novacodestudios.model.AddAnswer
import com.novacodestudios.model.Answer
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnswerApi {
    @POST("answer")
    suspend fun addAnswers(@Body answers: List<AddAnswer>):List<Answer>

    @GET("answer/appointment/{id}")
    suspend fun getAnswersByAppointmentId(@Path("id") id: Int):List<Answer>

}