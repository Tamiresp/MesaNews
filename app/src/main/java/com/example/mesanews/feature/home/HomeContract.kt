package com.example.mesanews.feature.home

import com.example.mesanews.adapter.NewsAdapter
import com.synnapps.carouselview.ImageListener

interface HomeContract {
    interface View{
        fun loadCarousel(): ImageListener

        fun initRecyclerView()

        fun showProgress()

        fun hideProgress()
    }

    interface Presenter{
        fun getData(adapter: NewsAdapter, token: String)
    }
}