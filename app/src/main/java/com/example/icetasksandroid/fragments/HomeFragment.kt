package com.example.icetasksandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.icetasksandroid.R
import com.example.icetasksandroid.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonNavigateToSecond.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
        }

        binding.buttonNavigateToAddData.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragment3AddData)
        }

        binding.buttonNavigateToViewData.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragment4ViewData)
        }

        binding.buttonNavigateToMap.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragment5Map)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}