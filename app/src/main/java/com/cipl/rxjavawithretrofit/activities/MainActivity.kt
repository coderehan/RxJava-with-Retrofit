package com.cipl.rxjavawithretrofit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.cipl.rxjavawithretrofit.R
import com.cipl.rxjavawithretrofit.adapters.MyFoodItemAdapter
import com.cipl.rxjavawithretrofit.api.Retrofit
import com.cipl.rxjavawithretrofit.databinding.ActivityMainBinding
import com.cipl.rxjavawithretrofit.models.FoodResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var foodAdapter: MyFoodItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setFoodRecyclerView()
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setFoodRecyclerView() {
        binding.progressBar.visibility = View.GONE
        foodAdapter = MyFoodItemAdapter(this, ArrayList<FoodResponse>())
        binding.rvFood.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = foodAdapter       // Passing adapter to recyclerview adapter
        }

        // Now combine both Observables & Observer of RxJava together
        // CompositeDisposable is like container which will contain bothObservables & Observer of RxJava
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            getObservables().subscribeOn(Schedulers.io())       // Observables is always executed on background thread
                .observeOn(AndroidSchedulers.mainThread())                              // Observing data on UI thread
                .subscribe({ response -> getObserver(response as List<FoodResponse>) },
                    { t -> onFailure(t) })
        )
    }

    // Calling API interface function in background thread using RxJava Observables
    private fun getObservables(): Observable<FoodResponse> {
        return Retrofit.api.getAllData()
    }

    // Now we need to observe data from observables of RxJava
    private fun getObserver(foodList: List<FoodResponse>) {
        if (foodList.isNotEmpty()) {
            foodAdapter.setData(foodList)   // Setting data in adapter class after getting data from api and adapter will set data in recyclerview
        }
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(applicationContext, "$t", Toast.LENGTH_LONG).show()
    }
}