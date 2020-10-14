package com.example.mesanews.feature.filter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.mesanews.R
import com.example.mesanews.feature.home.HomeActivity
import com.example.mesanews.util.Constants.DATE
import kotlinx.android.synthetic.main.activity_filter.*
import java.util.*


class FilterActivity : AppCompatActivity() {
    private val sp: SharedPreferences by lazy {
        getSharedPreferences(DATE, Context.MODE_PRIVATE)
    }

    private var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        supportActionBar?.title = getString(R.string.filter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            date = "$year-$month-$dayOfMonth"
        }

        button_filter.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(DATE, date)
            startActivity(intent)
        }

        button_clean.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(DATE, "")
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}