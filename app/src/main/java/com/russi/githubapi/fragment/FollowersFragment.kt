package com.russi.githubapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.russi.githubapi.DetailActivity
import com.russi.githubapi.R
import com.russi.githubapi.adapter.ListDataFollowAdapter
import com.russi.githubapi.model.FollowModel
import com.russi.githubapi.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.fragment_following.view.*

class FollowersFragment : Fragment() {

    private lateinit var dataFollowing: UserViewModel
    private lateinit var listFollowAdapter: ListDataFollowAdapter

    companion object {
        fun View.showLoading() {
            visibility = View.VISIBLE
        }

        fun View.hideLoading() {
            visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listFollowAdapter = ListDataFollowAdapter()
        dataFollowing = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        view.rv_following.adapter = listFollowAdapter

        val act = activity as DetailActivity
        dataFollowing.getFollower((act.getUsername().toString()))
        dataFollowing.getFollowers
            .observe(viewLifecycleOwner, Observer {  followers ->
                listFollowAdapter.setData(followers as ArrayList<FollowModel>)
            })
        dataFollowing.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                progressBar.showLoading()
            } else {
                progressBar.hideLoading()
            }
        })
        dataFollowing.message.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
    }
}