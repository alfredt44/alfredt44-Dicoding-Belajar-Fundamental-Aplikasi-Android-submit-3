package com.dicoding.submissionbfaa3.ui.detail.viewpager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionbfaa3.R
import com.dicoding.submissionbfaa3.databinding.FragmentFollowerBinding
import com.dicoding.submissionbfaa3.ui.adapter.UserAdapter
import com.dicoding.submissionbfaa3.ui.detail.DetailActivity
import com.dicoding.submissionbfaa3.ui.detail.DetailViewModel
import com.dicoding.submissionbfaa3.util.Resource

class FollowerFragment : Fragment() {
    private val binding: FragmentFollowerBinding by viewBinding()
    private val viewModel: DetailViewModel by activityViewModels()
    private var adapter = UserAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recycleView.layoutManager = LinearLayoutManager(context)
            recycleView.adapter = this@FollowerFragment.adapter
            viewModel.getListFollowers().observe(viewLifecycleOwner, {
                progressBar.visibility = View.VISIBLE
                when (it) {
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        if (it.data!!.isEmpty()){
                            noData.visibility = View.VISIBLE
                        }else {
                            adapter.setList(it.data)
                            noData.visibility = View.GONE
                            adapter.onItemClick = { user ->
                                val intent = Intent(context, DetailActivity::class.java)
                                intent.putExtra("username", user.login)
                                startActivity(intent)
                            }

                        }
                    }
                    is Resource.Failure -> {
                        noData.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }

}