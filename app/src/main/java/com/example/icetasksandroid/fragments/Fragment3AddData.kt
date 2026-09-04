package com.example.icetasksandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.icetasksandroid.database.DatabaseHelper
import com.example.icetasksandroid.databinding.Fragment3AddDataBinding

class Fragment3AddData : Fragment() {

    private var _binding: Fragment3AddDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment3AddDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        binding.buttonSave.setOnClickListener {
            saveUserData()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveUserData() {
        val name = binding.editTextName.text.toString().trim()
        val number = binding.editTextNumber.text.toString().trim()

        if (name.isEmpty() || number.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val result = dbHelper.insertUser(name, number)

        if (result > 0) {
            Toast.makeText(requireContext(), "User saved successfully!", Toast.LENGTH_SHORT).show()
            binding.editTextName.text.clear()
            binding.editTextNumber.text.clear()
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Error saving user", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}