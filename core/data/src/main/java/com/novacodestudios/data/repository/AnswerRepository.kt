package com.novacodestudios.data.repository

import com.novacodestudios.network.api.AnswerApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnswerRepository(
    private val api: AnswerApi,
    private val dispatcher: CoroutineDispatcher= Dispatchers.IO
) {
    suspend fun getAnswersByAppointmentId(id:Int) = withContext(dispatcher){
        runCatching {
            api.getAnswersByAppointmentId(id)
        }
    }

    suspend fun addAnswer(answers:List<com.novacodestudios.model.AddAnswer>) = withContext(dispatcher){
        runCatching {
            api.addAnswers(answers)
        }
    }
}