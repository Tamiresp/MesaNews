package com.example.mesanews.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mesanews.R
import com.example.mesanews.adapter.NewsAdapter
import com.example.mesanews.data.entity.AllResults
import com.example.mesanews.data.entity.News
import com.example.mesanews.feature.filter.FilterFragment
import com.example.mesanews.feature.login.LoginActivity
import com.example.mesanews.util.Constants.TOKEN
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class HomeActivity : AppCompatActivity(), HomeContract.View {
    private val presenter = HomePresenter()

    private val items = ArrayList<News>()

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

        val token = intent.getStringExtra(TOKEN)

        if (token != null) {
            presenter.getData(adapter, token)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.action_filter -> {
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
}