package com.russi.githubapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.russi.githubapi.DetailActivity
import com.russi.githubapi.R
import com.russi.githubapi.adapter.ListDataFollowAdapter
import com.russi.githubapi.model.FollowModel
import com.russi.githubapi.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.fragment_follower.view.*
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.fragment_following.view.*

class FollowersFragment : Fragment() {

    private lateinit var dataFollowers: UserViewModel
    private lateinit var listFollowAdapter: ListDataFollowAdapter

    companion object {
        fun View.showLoading() {
            visibility = View.VISIBLE
        }

        fun View.hideLoading() {
            visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataFollowers = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        val activity  = activity as DetailActivity
        dataFollowers.getFollower(activity.getUsername().toString())
        dataFollowers.getFollowers.observe(viewLifecycleOwner, Observer{ followers ->
            listFollowAdapter = ListDataFollowAdapter()
            view.rv_follower.adapter = listFollowAdapter
            view.rv_follower.layoutManager = LinearLayoutManager(context)
            listFollowAdapter.setData(followers)
        })

        dataFollowers.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                pb_follower.showLoading()
            } else {
                pb_follower.hideLoading()
            }
        })
        dataFollowers.message.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
    }
}