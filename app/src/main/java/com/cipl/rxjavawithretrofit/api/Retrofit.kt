package com.cipl.rxjavawithretrofit.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {

    companion object {
        private val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://www.codingwithjks.tech/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        val api:FoodAPI = retrofit.create(FoodAPI::class.java)
    }
}