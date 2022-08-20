package com.example.geomemories.ui.feed

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.geomemories.database.Event
import com.example.geomemories.databinding.FeedListEventBinding
import java.text.SimpleDateFormat
import java.util.*

class FeedListAdapter(private val onItemClicked: (Event) -> Unit) :
    ListAdapter<Event, FeedListAdapter.EventViewHolder>(DiffCallback) {

    // Inflate feed_list_event.xml layout into a view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            FeedListEventBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    // View bind an Event object to it's corresponding feed_list_event.xml layout
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val current: Event = getItem(position)

        // Function called when the list item in the view holder is clicked
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class EventViewHolder(private var binding: FeedListEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.eventNotes.text = event.notes
            binding.eventDate.text = SimpleDateFormat("h:mm a").format(Date(event.date.toLong() * 1000))
            binding.eventTime.text = SimpleDateFormat("EEE, MMM d, yyyy").format(Date(event.date.toLong() * 1000))
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldEvent: Event, newEvent: Event): Boolean {
                return oldEvent.id == newEvent.id
            }

            override fun areContentsTheSame(oldEvent: Event, newEvent: Event): Boolean {
                return oldEvent == newEvent
            }
        }
    }
}