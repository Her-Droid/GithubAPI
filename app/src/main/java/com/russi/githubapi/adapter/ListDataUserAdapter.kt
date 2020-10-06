package com.russi.githubapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.russi.githubapi.DetailActivity
import com.russi.githubapi.DetailActivity.Companion.EXTRA_DATA
import com.russi.githubapi.R
import com.russi.githubapi.model.DataUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.list_user.view.*


class ListDataUserAdapter(val context: Context, val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var setData = arrayListOf<DataUser>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgUser: CircleImageView = itemView.image_user
        private val nameUser: TextView = itemView.name_user
        private val username: TextView = itemView.username

        fun bind(dataUser: DataUser) {
            nameUser.text = dataUser.username
            username.text = String.format(": %s", dataUser.name)
            Glide.with(context).load(dataUser.avatar).into(imgUser)

            itemView.setOnClickListener {
                itemClickListener.onItemClickListener(dataUser)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClickListener(dataUser: DataUser)

    }

    fun setDataUser(User: List<DataUser>) {
        setData.clear()
        setData.addAll(User)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return setData.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            (holder as ViewHolder).bind(setData[position])
        }
    }
}





