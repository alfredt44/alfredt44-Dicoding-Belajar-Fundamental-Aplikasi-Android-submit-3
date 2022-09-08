package com.dicoding.submissionbfaa3.ui.detail.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            FOLLOWERS -> FollowerFragment()
            FOLLOWING -> FollowingFragment()

            else -> FollowerFragment()
        }
    }

    companion object {
        const val FOLLOWERS = 0
        const val FOLLOWING = 1

    }
}