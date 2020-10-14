package com.example.mesanews.feature.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mesanews.R
import com.example.mesanews.adapter.NewsAdapter
import com.example.mesanews.data.entity.News
import com.example.mesanews.feature.filter.FilterActivity
import com.example.mesanews.feature.login.LoginActivity
import com.example.mesanews.util.Constants
import com.example.mesanews.util.Constants.DATE
import com.example.mesanews.util.Constants.TITLE
import com.example.mesanews.util.Constants.TOKEN_SAVE
import com.google.android.material.snackbar.Snackbar
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item.*

class HomeActivity : AppCompatActivity(), HomeContract.View {
    private val presenter = HomePresenter()

    private val items = ArrayList<News>()

    private val sp: SharedPreferences by lazy {
        getSharedPreferences(Constants.TOKEN_SAVE, Context.MODE_PRIVATE)
    }

    private val adapter: NewsAdapter by lazy {
        NewsAdapter(items, this)
    }

    override fun initRecyclerView() {
        recycler.adapter = adapter
    }

    private var sampleImages = intArrayOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_foreground
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.title = getString(R.string.news)

        var carouselView: CarouselView = findViewById(R.id.carouselView)
        carouselView.pageCount = sampleImages.size
        carouselView.setImageListener(loadCarousel())

        presenter.view = this

        initRecyclerView()

        val intent = intent
        val date = intent.getStringExtra(DATE)
        val title = intent.getStringExtra(TITLE)

        if (isDeviceConnected()) {
            val tokenSave = getPreferences()
            if (date != "" && date != null)
                tokenSave?.let { presenter.getDataForDate(adapter, it, date) }
            else
                tokenSave?.let { presenter.getData(adapter, it) }
        } else {
            Snackbar.make(
                findViewById(R.id.home_layout), R.string.no_network, Snackbar.LENGTH_LONG).show()
        }

        if (title != "" && title != null){
            val position: Int = findPosition(items, title)
            if (position != -1) {
                val list = ArrayList<News>()
                list.add(items[position])
                initRecyclerView()

                recycler.scrollToPosition(position)
            } else {
                return
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.action_filter -> {
                val intent = Intent(this, FilterActivity::class.java)
                startActivity(intent)
            }
            R.id.action_favorites -> {
                getPreferences()?.let { presenter.getDataHighlights(adapter, it) }
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun loadCarousel(): ImageListener {
        return ImageListener { position, imageView -> imageView.setImageResource(sampleImages[position]) }
    }

    override fun showProgress() {
        progressBarHome.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarHome.visibility = View.GONE
    }

    override fun getPreferences(): String? {
        return sp.getString(TOKEN_SAVE, "")
    }

    override fun setNoData(){
        txt_no_data.visibility = View.VISIBLE
    }

    private fun findPosition(list: List<News>, name: String?): Int {
        for (i in list.indices) {
            if (list[i].title.toLowerCase() == name)
                return i
        }
        return -1
    }

    private fun isDeviceConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    override fun setFavorite(){
        if (btnFavorite != null)
            btnFavorite.isChecked = true
    }
}