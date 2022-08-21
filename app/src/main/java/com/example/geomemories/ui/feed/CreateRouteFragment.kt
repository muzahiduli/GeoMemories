package com.example.geomemories.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.geomemories.GeoMemoriesApplication
import com.example.geomemories.R
import com.example.geomemories.databinding.FragmentCreateRouteBinding


class CreateRouteFragment : Fragment() {

    private var __binding: FragmentCreateRouteBinding? = null
    private val binding get() = __binding!!

    private val viewModel: FeedViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as GeoMemoriesApplication).database.eventDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        __binding = FragmentCreateRouteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startRouteButton.setOnClickListener {
            viewModel
        }

        binding.stopRouteButton.setOnClickListener {

        }
    }

}