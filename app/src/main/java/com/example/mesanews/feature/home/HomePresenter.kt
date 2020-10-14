package com.example.mesanews.feature.home

import com.example.mesanews.R
import com.example.mesanews.adapter.NewsAdapter
import com.example.mesanews.data.entity.AllResults
import com.example.mesanews.data.entity.News
import com.example.mesanews.service.Api
import com.example.mesanews.service.RestApi
import kotlinx.android.synthetic.main.item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter: HomeContract.Presenter {
    lateinit var view: HomeContract.View

    override fun getData(adapter: NewsAdapter, token: String) {
        val result = Api.getInstance().create(RestApi::class.java).getAllNews(token)
        view.showProgress()
        result.enqueue(object : Callback<AllResults> {

            override fun onFailure(call: Call<AllResults>, t: Throwable) {
                view.hideProgress()
            }

            override fun onResponse(call: Call<AllResults>, response: Response<AllResults>) {
                val result: AllResults = response.body()!!
                val imgList: ArrayList<String> = ArrayList()

                for (item in result.list) {
                    adapter.addItem(News(item.title, item.description, item.highlight, item.url, item.image_url))
                    if (item.highlight)
                        view.setFavorite()
                }

                view.hideProgress()
            }
        })
    }
    override fun getDataForDate(adapter: NewsAdapter, token: String, date: String) {
        val result = Api.getInstance().create(RestApi::class.java).getNewsForDate(date, token)
        view.showProgress()
        result.enqueue(object : Callback<AllResults> {

            override fun onFailure(call: Call<AllResults>, t: Throwable) {
                view.hideProgress()
            }

            override fun onResponse(call: Call<AllResults>, response: Response<AllResults>) {
                val result: AllResults = response.body()!!

                for (item in result.list) {
                    adapter.addItem(News(item.title, item.description, item.highlight, item.url, item.image_url))
                    if (item.highlight)
                        view.setFavorite()
                }
                if (result.list.isEmpty())
                    view.setNoData()
                view.hideProgress()
            }
        })
    }
    override fun getDataHighlights(adapter: NewsAdapter, token: String) {
        val result = Api.getInstance().create(RestApi::class.java).getFavorites(token)
        view.showProgress()
        result.enqueue(object : Callback<AllResults> {

            override fun onFailure(call: Call<AllResults>, t: Throwable) {
                view.hideProgress()
            }

            override fun onResponse(call: Call<AllResults>, response: Response<AllResults>) {
                val result: AllResults = response.body()!!

                for (item in result.list) {
                    adapter.addItem(News(item.title, item.description, item.highlight, item.url, item.image_url))
                    if (item.highlight)
                        view.setFavorite()
                }
                if (result.list.isEmpty())
                    view.setNoData()
                view.hideProgress()

            }
        })
    }
}