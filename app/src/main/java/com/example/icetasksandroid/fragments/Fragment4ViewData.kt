package com.example.icetasksandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.icetasksandroid.adapters.UserAdapter
import com.example.icetasksandroid.database.DatabaseHelper
import com.example.icetasksandroid.databinding.Fragment4ViewDataBinding

class Fragment4ViewData : Fragment() {

    private var _binding: Fragment4ViewDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Fragment4ViewDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())

        // Setup RecyclerView
        userAdapter = UserAdapter(mutableListOf()) { user ->
            deleteUser(user)
        }

        binding.recyclerViewUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }

        // Load users
        loadUsers()

        binding.buttonRefresh.setOnClickListener {
            loadUsers()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadUsers() {
        val users = dbHelper.getAllUsers()
        if (users.isEmpty()) {
            binding.textViewNoData.visibility = View.VISIBLE
            binding.recyclerViewUsers.visibility = View.GONE
        } else {
            binding.textViewNoData.visibility = View.GONE
            binding.recyclerViewUsers.visibility = View.VISIBLE
            userAdapter.updateList(users)
        }
    }

    private fun deleteUser(user: com.example.icetasksandroid.models.UserData) {
        val result = dbHelper.deleteUser(user.id)
        if (result > 0) {
            userAdapter.removeItem(user)
            Toast.makeText(requireContext(), "User deleted successfully!", Toast.LENGTH_SHORT).show()
            if (dbHelper.getAllUsers().isEmpty()) {
                binding.textViewNoData.visibility = View.VISIBLE
                binding.recyclerViewUsers.visibility = View.GONE
            }
        } else {
            Toast.makeText(requireContext(), "Error deleting user", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}