package com.example.contactsmanager.viewUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsmanager.R
import com.example.contactsmanager.databinding.CardItemBinding
import com.example.contactsmanager.room.User

class MyRecyclerViewAdapter(
    private val usersList: List<User>,
    private val clickListener: (User) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : CardItemBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.card_item, parent, false)

        binding.tvEmail.isSelected = true
        binding.tvName.isSelected = true
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position], clickListener)
    }
}


class MyViewHolder(private val binding : CardItemBinding)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(user : User, clickListener: (User) -> Unit){
        binding.tvName.text = user.name
        binding.tvEmail.text = user.email

        binding.root.setOnClickListener(){
            clickListener(user)
        }
    }

}