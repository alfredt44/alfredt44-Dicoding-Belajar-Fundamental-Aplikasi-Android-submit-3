package com.dicoding.submissionbfaa3.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionbfaa3.R
import com.dicoding.submissionbfaa3.databinding.ActivityFavoriteBinding
import com.dicoding.submissionbfaa3.ui.adapter.UserAdapter
import com.dicoding.submissionbfaa3.ui.detail.DetailActivity
import com.dicoding.submissionbfaa3.util.Resource
import com.dicoding.submissionbfaa3.util.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private val binding : ActivityFavoriteBinding by viewBinding()
    private val adapter = UserAdapter()
    private lateinit var viewModel : FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this,factory).get(FavoriteViewModel::class.java)
        initUI()
    }

    private fun initUI() {
        title = getString(R.string.favorite)
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = this@FavoriteActivity.adapter
            setHasFixedSize(true)
        }
        viewModel.getListFavorite().observe(this,{
            when(it){
                is Resource.Success -> {
                    binding.progressBar2.visibility = View.GONE
                    if (it.data!!.isEmpty()){
                        binding.noData.visibility = View.VISIBLE
                    }else {
                    adapter.setList(it.data!!)
                    adapter.onItemClick = { user->
                        val intent = Intent(this,DetailActivity::class.java)
                        intent.putExtra("username",user.login)
                        startActivity(intent)
                    }
                        binding.noData.visibility = View.GONE
                    }

                }
                is Resource.Failure -> {
                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}