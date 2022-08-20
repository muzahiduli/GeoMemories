package com.example.geomemories.ui.feed

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.geomemories.GeoMemoriesApplication
import com.example.geomemories.MainActivity
import com.example.geomemories.databinding.FragmentCreateEventBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.File
import java.lang.Error
import java.net.URI


class CreateEventFragment : Fragment() {
    private var _binding: FragmentCreateEventBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: FeedViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as GeoMemoriesApplication).database.eventDao()
        )
    }

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var location: Location? = null
    private var uri: Uri? = null


    // Inflate layout and return the root element of it
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Location permission check
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                fusedLocationClient = (activity as MainActivity).fusedLocationClient
                fusedLocationClient!!.lastLocation.addOnSuccessListener {
                    location = it
                    binding.eventLocation.text = location!!.longitude.toString() + " " + location!!.latitude.toString()
                }
            }
        }

        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)

        binding.submitEntryButton.setOnClickListener {
            location?.let {
                val longitude: Double = location!!.longitude
                val latitude: Double = location!!.latitude

                viewModel.addEvent(
                    notes = binding.eventNotes.text.toString(),
                    date = (System.currentTimeMillis() / 1000L).toInt(),
                    longitude = longitude,
                    latitude = latitude
                )
                goBack()
            }
        }
        binding.cancelEntryButton.setOnClickListener {
            goBack()
        }

        // Capture image button
//        getExternalFilesDir(Environment.DIRECTORY_PICTURES), "EventPhoto.jpg")
        binding.captureImageButton.setOnClickListener {
            val takePictureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val file: File = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "EventPhoto.jpg")
            uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName + ".provider", file)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

            try {
                launchCamera.launch(takePictureIntent)
            } catch (e: Error) {}
        }

    }

    private val launchCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.entryImage.setImageURI(uri)
            }
        }

    // Navigate back to feed screen
    private fun goBack() {
        val action = CreateEventFragmentDirections.actionCreateEventFragmentToNavigationFeed()
        findNavController().navigate(action)
    }

    val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        // Do nothing with response for now
    }
}