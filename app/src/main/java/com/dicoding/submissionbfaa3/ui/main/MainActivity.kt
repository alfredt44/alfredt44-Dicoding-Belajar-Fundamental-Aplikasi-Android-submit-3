package com.dicoding.submissionbfaa3.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionbfaa3.R
import com.dicoding.submissionbfaa3.databinding.ActivityMainBinding
import com.dicoding.submissionbfaa3.ui.adapter.UserAdapter
import com.dicoding.submissionbfaa3.ui.credit.CreditActivity
import com.dicoding.submissionbfaa3.ui.detail.DetailActivity
import com.dicoding.submissionbfaa3.ui.favorite.FavoriteActivity
import com.dicoding.submissionbfaa3.ui.setting.SettingActivity
import com.dicoding.submissionbfaa3.util.Resource

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()
    private var adapter = UserAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

    }

    private fun initUI() {
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
            recycleView.adapter = this@MainActivity.adapter
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return if (query?.isNotBlank() == true) {
                        progressBar.visibility = View.VISIBLE
                        searchUser(query)
                        true
                    } else false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun searchUser(query: String?) {
        viewModel.searchUser(query).observe(this, {
            with(binding) {
                when (it) {
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        if (it.data!!.isEmpty()){
                            noData.visibility = View.VISIBLE
                        }else {
                            idle.visibility = View.GONE
                            adapter.setList(it.data)
                            adapter.onItemClick = { user ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra("username", user.login)
                                startActivity(intent)
                            }
                            noData.visibility = View.GONE
                        }
                    }
                    is Resource.Failure -> {
                        noData.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(select: Int) {
        when (select) {
            R.id.settings_menu -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
            R.id.Credit -> {

                startActivity(Intent(this@MainActivity, CreditActivity::class.java))
            }
            R.id.favmenu -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }


        }

    }
}