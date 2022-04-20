package com.sliide.usermanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sliide.usermanagement.R
import com.sliide.usermanagement.databinding.LayoutUserItemBinding
import com.sliide.usermanagement.models.UsersItem

class UsersListAdapter(
    private val usersList: List<UsersItem>,
    private val context:Context,
    private val usersItemCallback: UsersItemCallback,
) :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = DataBindingUtil.inflate<LayoutUserItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_user_item,
            parent,
            false
        )
        val viewHolder = ViewHolder(itemView)

        itemView.lyUserItem.setOnLongClickListener {
            setItemParentLongClick(
                viewHolder.adapterPosition
            )
            return@setOnLongClickListener true
        }
        return viewHolder
    }

    private fun setItemParentLongClick(adapterPosition: Int) {
        usersItemCallback.onItemLongClick(usersList[adapterPosition])
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(usersList[position],context)
    }

    class ViewHolder(private val itemBinding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(userItem: UsersItem,context: Context) {
            itemBinding.tvUserName.text = userItem.name
            itemBinding.tvUserEmail.text = userItem.email
            itemBinding.tvUserStatus.text = userItem.status

            if(userItem.status.equals("active",true))
                itemBinding.tvUserStatus.setTextColor(ContextCompat.getColor(context,R.color.active))
            else
                itemBinding.tvUserStatus.setTextColor(ContextCompat.getColor(context,R.color.inactive))

        }
    }

    interface UsersItemCallback {
        fun onItemLongClick(userItem: UsersItem)
    }
}