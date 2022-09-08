package com.dicoding.submissionbfaa3.ui.credit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.viewbinding.library.activity.viewBinding
import com.dicoding.submissionbfaa3.R
import com.dicoding.submissionbfaa3.databinding.ActivityCreditBinding
import com.dicoding.submissionbfaa3.ui.favorite.FavoriteActivity
import com.dicoding.submissionbfaa3.ui.setting.SettingActivity

class CreditActivity : AppCompatActivity() {
    private val binding : ActivityCreditBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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