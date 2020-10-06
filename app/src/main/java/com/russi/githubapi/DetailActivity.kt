package com.russi.githubapi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.russi.githubapi.adapter.ViewPagerDetailAdapter
import com.russi.githubapi.model.DataUser
import com.russi.githubapi.model.DetailResponse
import com.russi.githubapi.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailUser: UserViewModel

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailUser = ViewModelProvider(this).get(UserViewModel::class.java)
        viewPagerConfig()

        if (intent.hasExtra(EXTRA_DATA)) {
            val user = intent.getParcelableExtra<DataUser>(EXTRA_DATA)
            if (user?.username?.isNotEmpty()!!) {
                title = user.username
                detailUser.getDetailUser(user.username)
            } else {
                title = user.login
                detailUser.getDetailUser(user.login)
            }
        }
        val dataUser = intent.getParcelableExtra<DetailResponse>(EXTRA_DATA)
        detailUser.dataDetailUser.observe(this, Observer { response ->
            detail_followers.text = response.followers.toString()
            detail_following.text = response.following.toString()
            Glide.with(this).load(dataUser?.avatarUrl)
                .into(detail_image)
        })
    }


    private fun viewPagerConfig() {
        val viewPagerDetailAdapter = ViewPagerDetailAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetailAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

}