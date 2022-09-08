package com.dicoding.submissionbfaa3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionbfaa3.data.model.User
import com.dicoding.submissionbfaa3.databinding.UserRowItemBinding

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private var list: List<User> = emptyList()
    fun setList(newList: List<User>){
        this.list = newList
        this.notifyDataSetChanged()
    }
    class ListViewHolder(val binding: UserRowItemBinding) : RecyclerView.ViewHolder(binding.root)
    var onItemClick : ((User) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            UserRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       val data = list[position]
        with(holder.binding){
            uiTxtUrl.text = data.url
            uiTxtUsername.text = data.login
            Glide.with(uiImgAvatar.context).load(data.avatarUrl).into(uiImgAvatar)
            root.setOnClickListener { onItemClick?.invoke(data) }
        }
    }

    override fun getItemCount(): Int = list.size
}