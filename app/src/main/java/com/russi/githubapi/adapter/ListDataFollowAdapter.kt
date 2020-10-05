package com.russi.githubapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.russi.githubapi.R
import com.russi.githubapi.model.FollowModel
import kotlinx.android.synthetic.main.list_user.view.*

class ListDataFollowAdapter(private var listDataFollow: ArrayList<FollowModel> = arrayListOf()) :
    RecyclerView.Adapter<ListDataFollowAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgUser = itemView.image_user
        private var nameUser = itemView.name_user

        fun bind(followModel: FollowModel) {
            Glide.with(itemView.context)
                .load(followModel.avatarUrl)
                .into(imgUser)
            nameUser.text = followModel.login
        }
    }

    fun setData(view: List<FollowModel>) {
        listDataFollow.clear()
        listDataFollow.addAll(view)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_user, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDataFollow[position])
    }

    override fun getItemCount(): Int {
        return listDataFollow.size
    }
}



