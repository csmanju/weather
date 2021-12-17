package com.saiesh.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.myapplication.RestClint.RestClint
import com.hellohasan.weatherappmvvm.utils.kelvinToCelsius
import com.hellohasan.weatherappmvvm.utils.unixTimestampToDateTimeString
import com.hellohasan.weatherappmvvm.utils.unixTimestampToTimeString
import com.saiesh.weather.model.DataClass.DataModel.WeatherRes
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var myToolbar:Toolbar
    lateinit var textViewDate: TextView
    lateinit var textViewTempratureCity: TextView
    lateinit var textViewHumid: TextView
    lateinit var textViewPressure: TextView
    lateinit var textViewVisibility: TextView
    lateinit var textViewSunRise: TextView
    lateinit var textViewSunSet: TextView
    lateinit var textViewDesc: TextView
    lateinit var textViewCity: TextView
    lateinit var imageView: ImageView
    lateinit var relativeLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
   // lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        myToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(myToolbar)
        val actionBar = supportActionBar!!
        actionBar.title = "Weather"

       textViewDate=findViewById(R.id.tv_date_time)
       textViewTempratureCity=findViewById(R.id.tv_temperature)
       textViewCity=findViewById(R.id.tv_city_country)
       textViewHumid=findViewById(R.id.tv_humidity_value)
       textViewPressure=findViewById(R.id.tv_pressure_value)
       textViewVisibility=findViewById(R.id.tv_visibility_value)
       textViewSunRise=findViewById(R.id.tv_sunrise_time)
       textViewSunSet=findViewById(R.id.tv_sunset_time)
       textViewDesc=findViewById(R.id.tv_weather_condition)
       imageView=findViewById(R.id.iv_weather_condition)
       relativeLayout=findViewById(R.id.rel)
       progressBar=findViewById(R.id.progress_circular)





        val query="Bangalore"
        getWeatherData(query)

    }

    private fun getWeatherData(query: String?) {

        progressBar.visibility=View.VISIBLE
        relativeLayout.visibility=View.GONE

        var name: String? =query
        var id:String="b25af7ef902bb9894cf0e2052ce9cf56"
        if (name != null) {
            RestClint.getClient.callApiForWeatherInfo(name,id).enqueue(object : retrofit2.Callback<WeatherRes>{
                override fun onResponse(
                    call: Call<WeatherRes>,
                    response: Response<WeatherRes>
                ) {
                    if (response.isSuccessful){
                        val weatherResponse:WeatherRes?=response.body()
                        if (weatherResponse?.cod.equals("200")){
                            progressBar.visibility=View.GONE
                            relativeLayout.visibility=View.VISIBLE
                            Toast.makeText(applicationContext, "Its working", Toast.LENGTH_SHORT).show()

                            var url="http://openweathermap.org/img/w/"+weatherResponse?.weather?.get(0)?.icon+".png"

                            textViewDate.text=weatherResponse?.dt?.unixTimestampToDateTimeString()
                            textViewCity.text=weatherResponse?.name+", "+weatherResponse?.sys?.country
                            textViewTempratureCity.text= weatherResponse?.main?.temp?.kelvinToCelsius().toString()
                            textViewHumid.text= weatherResponse?.main?.humidity.toString()+"%"
                            textViewPressure.text= weatherResponse?.main?.pressure.toString()+"mBar"
                            textViewVisibility.text=(weatherResponse?.visibility?.div(1000.0)).toString()+"KM"
                            textViewDesc.text= weatherResponse?.weather?.get(0)?.description
                            textViewSunRise.text=
                                weatherResponse?.sys?.sunrise?.unixTimestampToTimeString().toString()
                            textViewSunSet.text=weatherResponse?.sys?.sunset?.unixTimestampToTimeString().toString()
                            Glide.with(this@MainActivity).load(url).into(imageView)




                        }
                    }
                }

                override fun onFailure(call: Call<WeatherRes>, t: Throwable) {
                    //TODO("Not yet implemented")
                    progressBar.visibility=View.GONE
                    relativeLayout.visibility=View.GONE
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    //println("#######################################################"+t.message.toString())
                }
            })
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu,menu)


        val search = menu!!.findItem(R.id.appSearchBar)
        val searchView:SearchView = search.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                 getWeatherData(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
               // adapter.filter.filter(newText)
                return false
            }
        })



        return super.onCreateOptionsMenu(menu)
    }
}