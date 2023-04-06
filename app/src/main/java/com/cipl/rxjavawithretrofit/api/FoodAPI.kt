package com.cipl.rxjavawithretrofit.api

import com.cipl.rxjavawithretrofit.models.FoodResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET


interface FoodAPI {

    @GET("food.php")    // All data are present in this end point only
    fun getAllData(): Observable<FoodResponse>     // Now all data are stored in Observables of RxJava
}