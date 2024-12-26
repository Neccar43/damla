package com.novacodestudios.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name= DonorPreferences.DATA_STORE_NAME)
class DonorPreferences @Inject constructor(
    private val context: Context
) {

    companion object{
        const val DATA_STORE_NAME="donor_id"
        private val DONOR_ID_KEY = intPreferencesKey("donor_id")
    }

    val getDonorId: Flow<Int?>
        get() = context.dataStore.data.map {userType->
            userType[DONOR_ID_KEY]
        }

    suspend fun setDonorId(donorId: Int) {
        context.dataStore.edit {preferences->
            preferences[DONOR_ID_KEY]= donorId
        }
    }
}