package com.example.password_manager_room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.password_manager_room.data.Account
import com.example.password_manager_room.databinding.ItemLayoutBinding

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(account: Account){
            binding.txtCompany.text = account.company
            binding.txtEmail.text = account.email
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Account>(){
        override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val account = differ.currentList[position]
        holder.bind(account)

        holder.itemView.setOnClickListener {
            onClick?.invoke(account)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick : ((Account) -> Unit)? = null
}