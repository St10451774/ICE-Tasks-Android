package com.example.icetasksandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.icetasksandroid.databinding.ItemUserBinding
import com.example.icetasksandroid.models.UserData

class UserAdapter(
    private val userList: MutableList<UserData>,
    private val onDeleteClick: (UserData) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserData) {
            binding.textViewName.text = "Name: ${user.name}"
            binding.textViewNumber.text = "Number: ${user.number}"
            binding.textViewTimestamp.text = "Added: ${user.timestamp}"

            binding.buttonDelete.setOnClickListener {
                onDeleteClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    fun removeItem(user: UserData) {
        val position = userList.indexOf(user)
        if (position >= 0) {
            userList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateList(newList: List<UserData>) {
        userList.clear()
        userList.addAll(newList)
        notifyDataSetChanged()
    }
}