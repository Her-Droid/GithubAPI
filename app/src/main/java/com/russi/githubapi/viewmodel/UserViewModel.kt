package com.russi.githubapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.russi.githubapi.apiservice.ApiClient
import com.russi.githubapi.model.DetailResponse
import com.russi.githubapi.model.FollowModel
import com.russi.githubapi.model.SearchResponse
import com.russi.githubapi.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel : ViewModel() {

    val searchDataUser = MutableLiveData<SearchResponse>()
    val dataDetailUser = MutableLiveData<DetailResponse>()
    val getFollowers = MutableLiveData<List<FollowModel>>()
    val getFollowing = MutableLiveData<List<FollowModel>>()

    val loading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()


    fun searchUser(username: String) {
        loading.value = true
        ApiClient.apiService.searchUser(username)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    loading.value = false
                    if (response.isSuccessful) {
                        searchDataUser.postValue(response.body())
                    } else {
                        message.value = response.message()
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    loading.value = false
                    message.value = t.message
                }
            })

    }


    fun getDetailUser(username: String) {
        ApiClient.apiService.detailUser(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        dataDetailUser.postValue(response.body())
                    } else {
                        message.value = response.message()
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    message.value = t.message
                }
            })
    }

    fun getFollower(username: String) {
        loading.value = true
        ApiClient.apiService.getFollowers(username)
            .enqueue(object : Callback<List<FollowModel>> {
                override fun onResponse(
                    call: retrofit2.Call<List<FollowModel>>,
                    response: Response<List<FollowModel>>
                ) {
                    loading.value = false
                    if (response.isSuccessful) {
                        getFollowers.postValue(response.body())
                    } else {
                        message.value = response.message()
                    }
                }

                override fun onFailure(call: retrofit2.Call<List<FollowModel>>, t: Throwable) {
                    loading.value = false
                    message.value = t.message
                }
            })
    }

    fun getFollowing(username: String) {
        loading.value = true
        ApiClient.apiService.getFollowing(username)
            .enqueue(object : Callback<List<FollowModel>> {
                override fun onResponse(
                    call: retrofit2.Call<List<FollowModel>>,
                    response: Response<List<FollowModel>>
                ) {
                    loading.value = false
                    if (response.isSuccessful) {
                        getFollowing.postValue(response.body())
                    } else {
                        message.value = response.message()
                    }
                }

                override fun onFailure(call: retrofit2.Call<List<FollowModel>>, t: Throwable) {
                    loading.value = false
                    message.value = t.message
                }
            })
    }
}