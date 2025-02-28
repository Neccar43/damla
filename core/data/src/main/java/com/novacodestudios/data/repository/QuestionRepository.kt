package com.novacodestudios.data.repository

import com.novacodestudios.network.api.QuestionApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionRepository(
    private val api: QuestionApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getQuestions() = withContext(dispatcher) {
        runCatching {
            api.getQuestions()
        }
    }
}