package com.example.icetasksandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.icetasksandroid.api.RetrofitInstance
import com.example.icetasksandroid.databinding.FragmentSecondBinding
import kotlinx.coroutines.launch

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFetchPosts.setOnClickListener {
            fetchPostsFromAPI()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun fetchPostsFromAPI() {
        binding.progressBar.visibility = View.VISIBLE
        binding.textViewPosts.text = "Loading posts..."

        lifecycleScope.launch {
            try {
                val posts = RetrofitInstance.apiService.getAllPosts()
                binding.progressBar.visibility = View.GONE

                val postsText = posts.take(5).joinToString("\n\n") { post ->
                    "ID: ${post.id}\nTitle: ${post.title}\nBody: ${post.body}"
                }

                binding.textViewPosts.text = postsText
                Toast.makeText(requireContext(), "Posts fetched successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                binding.textViewPosts.text = "Error: ${e.message}"
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}