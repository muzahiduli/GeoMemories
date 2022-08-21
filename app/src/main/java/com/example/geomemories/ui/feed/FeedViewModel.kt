package com.example.geomemories.ui.feed

import android.location.Location
import androidx.lifecycle.*
import com.example.geomemories.database.Event
import com.example.geomemories.database.EventDao
import kotlinx.coroutines.launch

class FeedViewModel(private val eventDao: EventDao) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is feed Fragment"
    }
    val text: LiveData<String> = _text

    val events: LiveData<List<Event>> = eventDao.getEvents().asLiveData()

    fun addEvent(notes: String, date: Int, latitude: Double, longitude: Double, image: String? = null) {
        val newEvent = Event(notes=notes, date = date, longitude = longitude, latitude = latitude, image = image)
        viewModelScope.launch {
            eventDao.addEvent(newEvent)
        }
    }


}

class InventoryViewModelFactory(private val eventDao: EventDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedViewModel(eventDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}