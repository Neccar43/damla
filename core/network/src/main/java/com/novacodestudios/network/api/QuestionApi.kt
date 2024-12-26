package com.novacodestudios.network.api

import com.novacodestudios.model.Question
import retrofit2.http.GET

interface QuestionApi {
    @GET("questions")
    suspend fun getQuestions(): List<Question>
}