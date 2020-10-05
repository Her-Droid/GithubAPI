package com.russi.githubapi.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.russi.githubapi.R
import com.russi.githubapi.fragment.FollowersFragment
import com.russi.githubapi.fragment.FollowingFragment

class ViewPagerDetailAdapter(private val context: Context, fragmentManager: FragmentManager):
        FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val page = listOf(FollowersFragment(), FollowingFragment()
    )

    override fun getCount(): Int {
        return page.size
    }

    @StringRes
    private val tabTitle = intArrayOf(
        R.string.followng,
        R.string.follows
    )

    override fun getItem(position: Int): Fragment {
        return page[position]
        }
    override fun getPageTitle(position: Int): CharSequence? {
        return  context.resources.getString(tabTitle[position])
    }
    }

