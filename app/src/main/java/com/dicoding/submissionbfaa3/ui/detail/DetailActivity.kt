package com.dicoding.submissionbfaa3.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submissionbfaa3.R
import com.dicoding.submissionbfaa3.data.model.User
import com.dicoding.submissionbfaa3.databinding.ActivityDetailBinding
import com.dicoding.submissionbfaa3.ui.credit.CreditActivity
import com.dicoding.submissionbfaa3.ui.detail.viewpager.ViewPagerAdapter
import com.dicoding.submissionbfaa3.ui.detail.viewpager.ViewPagerAdapter.Companion.FOLLOWERS
import com.dicoding.submissionbfaa3.ui.detail.viewpager.ViewPagerAdapter.Companion.FOLLOWING
import com.dicoding.submissionbfaa3.ui.favorite.FavoriteActivity
import com.dicoding.submissionbfaa3.ui.setting.SettingActivity
import com.dicoding.submissionbfaa3.util.Resource
import com.dicoding.submissionbfaa3.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import okio.IOException

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by viewBinding()
    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intent.getStringExtra("username").let { username ->
            title = username
            viewModel.username = username
            viewModel.getDetail(username).observe(this, {
                when (it) {
                    is Resource.Success -> {
                        val data = it.data!!
                        with(binding) {
                            progressBar.visibility = View.GONE
                            Glide.with(this@DetailActivity).load(data.avatarUrl)
                                .into(udDetailAvatar)
                            udDetailName.text = data.name
                            udDetailUsername.text = data.login
                            udDetailRepository.text = "${data.publicRepos} Repository"
                            if (!data.location.isNullOrBlank()) {
                                detailLocation.visibility = View.VISIBLE
                                detailLocation.text = "Location : ${data.location}"
                            } else {
                                detailLocation.visibility = View.GONE
                            }
                        }
                        checkFavorite(data)
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        setupViewPager()
    }

    private fun checkFavorite(data: User) {
        with(binding) {
            viewModel.isFavorite(data.id).observe(this@DetailActivity, {
                when (it) {
                    1 -> {
                        fab.apply {
                            setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this@DetailActivity,
                                    R.drawable.ic_baseline_favorite_24
                                )
                            )
                            setOnClickListener {
                                try {
                                    viewModel.deleteFavorite(data)
                                    Toast.makeText(
                                        this@DetailActivity.applicationContext,
                                        getString(R.string.deleted),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } catch (e: IOException) {
                                    Toast.makeText(
                                        this@DetailActivity.applicationContext,
                                        e.message,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                    }
                    0 -> {
                        fab.apply {
                            setImageDrawable(
                                AppCompatResources.getDrawable(
                                    this@DetailActivity.applicationContext,
                                    R.drawable.ic_baseline_favorite_border_24
                                )
                            )
                            setOnClickListener {
                                try {
                                    Toast.makeText(
                                        this@DetailActivity.applicationContext,
                                        getString(R.string.added),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    viewModel.addToFavorite(data)
                                } catch (e: IOException) {
                                    Toast.makeText(
                                        this@DetailActivity,
                                        e.message,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                    }
                }
            })
        }

    }


    private fun setupViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this)
        val viewPager2 = binding.udViewPager
        viewPager2.adapter = viewPagerAdapter
        val tabLayout: TabLayout = binding.udTabs
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                FOLLOWERS -> tab.text = getString(R.string.followers)
                FOLLOWING -> tab.text = getString(R.string.following)
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.settings_menu -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
            R.id.Credit -> {

                startActivity(Intent(this, CreditActivity::class.java))
            }
            R.id.favmenu -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}