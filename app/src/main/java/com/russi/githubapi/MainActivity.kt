package com.russi.githubapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.russi.githubapi.adapter.ListDataUserAdapter
import com.russi.githubapi.model.DataUser
import com.russi.githubapi.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ListDataUserAdapter.ItemClickListener {

    private var listData = arrayListOf<DataUser>()
    private lateinit var listDataUserAdapter: ListDataUserAdapter
    lateinit var userViewModel: UserViewModel

    companion object {
        const val SIZE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        listDataUserAdapter = ListDataUserAdapter(this, this)
        rv_user.adapter = listDataUserAdapter

        showLoading()
        getSearchUser()

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

    private fun getSearchUser() {
        search_user.apply {
            setIconifiedByDefault(true)
            isFocusable = false
            isIconified = false
            clearFocus()
            requestFocusFromTouch()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userViewModel.searchUser(query!!).observe(this@MainActivity, Observer { response ->
                            when {
                                response.items.size >= SIZE -> {
                                    for (i in 0 until SIZE) {
                                        val dataUser = DataUser()
                                        dataUser.login = response.items[i].login
                                        dataUser.name = response.items[i].name
                                        dataUser.avatarUrl = response.items[i].avatarUrl
                                        listData.add(dataUser)
                                    }
                                    listDataUserAdapter.setDataUser(listData)
                                }
                                response.items.size < SIZE -> {
                                    listData.clear()
                                    for (item in response.items) {
                                        val dataUser = DataUser()
                                        dataUser.login = item.login
                                        dataUser.avatarUrl = item.avatarUrl
                                        dataUser.name = item.name
                                        listData.add(dataUser)
                                    }
                                    listDataUserAdapter.setDataUser(listData)
                                }
                            }
                        })
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty()!!) {
                        getUser()
                    }
                    return true
                }
            })
        }
    }

    private fun getUser() {
        listData.clear()
        userViewModel.searchUser("username")
        listDataUserAdapter.setDataUser(listData)

    }

    override fun onItemClickListener(dataUser: DataUser) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("username", dataUser)
        startActivity(intent)
    }
}







