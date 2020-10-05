package com.russi.githubapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.russi.githubapi.adapter.ListDataUserAdapter
import com.russi.githubapi.model.DataUser
import com.russi.githubapi.model.UserResponse
import com.russi.githubapi.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var listData: ArrayList<DataUser> = ArrayList()
    private lateinit var listDataUserAdapter: ListDataUserAdapter
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        getUser()
        showLoading()
        searchUser()

        userViewModel.searchDataUser
            .observe(this@MainActivity, { response ->
                listData.clear()
                for (item in response.items) {
                    val user = DataUser()
                    user.login = item.login
                    user.avatarUrl = item.avatarUrl
                    listData.add(user)
                }

                listDataUserAdapter = ListDataUserAdapter()
                rv_user.layoutManager = LinearLayoutManager(this)
                rv_user.adapter = listDataUserAdapter
                listDataUserAdapter.listUser = listData
            })
    }

    private fun searchUser() {
        search_user.apply {
            setIconifiedByDefault(true)
            isFocusable = false
            isIconified = false
            clearFocus()
            requestFocusFromTouch()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userViewModel.searchUser(query!!)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isEmpty()!!) {
                        getUser()
                    }
                    return true
                }

            })
        }
    }

    private fun getUser() {
        listData.clear()
        val listUser = Gson().fromJson(getString(R.string.user_dashboard), UserResponse::class.java)
        listData.addAll(listUser.users)
        listDataUserAdapter = ListDataUserAdapter()
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = listDataUserAdapter

        listDataUserAdapter.listUser = listData
    }


    private fun showLoading() {
        userViewModel.loading.observe(this, { state ->
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })
        userViewModel.message.observe(this, { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
    }

}