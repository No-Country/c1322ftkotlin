package com.nocuntry.c1322ftkotlin.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ApodList(private val apiService: NasaApiService) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getApodList(): State<List<ApodResponse>> {
        val startDate = LocalDate.now().minusDays(10)
        val endDate = LocalDate.now()
        val dateRange = (0..endDate.toEpochDay() - startDate.toEpochDay()).map {
            startDate.plusDays(it)
        }

        val apodListState = mutableStateOf<List<ApodResponse>>(emptyList())

        for (date in dateRange) {
            val formattedDate = date.format(DateTimeFormatter.ISO_DATE)
            val response = apiService.getApod(formattedDate)
            if (response.isSuccessful) {
                val apod = response.body()
                if (apod != null) {
                    withContext(Dispatchers.Main) {
                        apodListState.value = apodListState.value + apod
                    }
                }
            }
        }

        return apodListState
    }
}
