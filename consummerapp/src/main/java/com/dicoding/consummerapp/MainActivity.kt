package com.dicoding.consummerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.activity.viewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.consummerapp.ProviderUtil.CONTENT_URI
import com.dicoding.consummerapp.adapter.UserAdapter
import com.dicoding.consummerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()
    private val adapter = UserAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFavoriteAsync()
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteAsync()
    }

    @DelicateCoroutinesApi
    private fun loadFavoriteAsync() {
        binding.progressCircular.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFav = async(Dispatchers.IO) {
                val cursor =
                    contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorite = deferredFav.await()
            Log.d("TAG", favorite.toString())
            adapter.setList(favorite)
            with(binding) {
                progressCircular.visibility = View.GONE
                rvProvider.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = this@MainActivity.adapter
                }
            }
        }
    }
}