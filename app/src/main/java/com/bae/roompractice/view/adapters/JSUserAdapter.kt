package com.bae.roompractice.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bae.roompractice.databinding.ItemUserLayoutBinding
import com.bae.roompractice.model.entities.JSUser
import com.bae.roompractice.utils.SimpleLog

class JSUserAdapter(private val activity: Activity): RecyclerView.Adapter<JSUserAdapter.ViewHolder>()
{
    private var users: List<JSUser> = listOf()

    class ViewHolder(view: ItemUserLayoutBinding): RecyclerView.ViewHolder(view.root){
        val tvName = view.tvNameValue
        val tvAge = view.tvAgeValue
        val tvPhone = view.tvPhoneValue
        val tvSex = view.tvSexValue
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserLayoutBinding = ItemUserLayoutBinding.inflate(
            LayoutInflater.from(activity),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        holder.tvName.text = user.name
        holder.tvAge.text = user.age
        holder.tvPhone.text = user.phone
        holder.tvSex.text = user.sex
    }

    override fun getItemCount(): Int {
        SimpleLog.i("Item Count ------> ${users.size}")
        return users.size
    }

    fun usersList(list: List<JSUser>) {
        users = list
        notifyDataSetChanged()
    }
}