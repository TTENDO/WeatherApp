package com.ttendo.weatherapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.ttendo.weatherapp.data.RetrofitInstance
import com.ttendo.weatherapp.data.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
//    val CITY: String = "dhaka,bd"
//    val API: String = "373922f8e4e1f9b8b1a25e5d9b1906eb"
    val apiKey : String = "373922f8e4e1f9b8b1a25e5d9b1906eb"
    lateinit var cityName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the city
        //get the button
        //onclicking the button call the getWeatger method
        //return the response to the screen

       //cityName = "Kampala"
        //weatherTask().execute()

        findViewById<Button>(R.id.search_btn).setOnClickListener{
                cityName = findViewById<EditText>(R.id.city).text.toString()
                if(cityName != null){
                    weatherTask().execute()
                }


//            CoroutineScope(Dispatchers.IO).launch {
//               val service = RetrofitInstance().createRetrofit()
//                cityName = findViewById<EditText>(R.id.city).text.toString()
//
//                val apiResponse = service.getWeather(cityName, apiKey)
//
//
//                withContext(Dispatchers.Main) {
////                    val textview = findViewById<TextView>(R.id.address)
////                    textview.text = apiResponse.toString()
//
//                    /* Extracting JSON returns from the API */
//                    val jsonObj = JSONObject(apiResponse.toString())
//                    val main = jsonObj.getJSONArray("main")
//                    val sys = jsonObj.getJSONArray("sys")
//                    val wind = jsonObj.getJSONArray("wind")
//                    val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
//                    val updatedAt: Long = jsonObj.getLong("dt")
//                    val updatedAtText =
//                        "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
//                            Date(updatedAt * 1000)
//                        )
//                    val temp = main.getJSONObject(0).getString("temp") + "°C"
//                    val tempMin = "Min Temp: " + main.getJSONObject(2).getString("temp_min") + "°C"
//                    val tempMax = "Max Temp: " + main.getJSONObject(3).getString("temp_max") + "°C"
//                    val pressure = main.getJSONObject(4).getString("pressure")
//                    val humidity = main.getJSONObject(5).getString("humidity")
//                    val sunrise: Long = sys.getJSONObject(1).getLong("sunrise")
//                    val sunset: Long = sys.getJSONObject(2).getLong("sunset")
//                    val windSpeed = wind.getJSONObject(0).getString("speed")
//                    val weatherDescription = weather.getString("description")
//                    val address = jsonObj.getString("name") + ", " + sys.getJSONObject(0).getString("country")
//
//                    /* Populating extracted data into our views */
//                    findViewById<TextView>(R.id.address).text = address
//                    findViewById<TextView>(R.id.updated_at).text = updatedAtText
//                    findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
//                    findViewById<TextView>(R.id.temp).text = temp
//                    findViewById<TextView>(R.id.temp_min).text = tempMin
//                    findViewById<TextView>(R.id.temp_max).text = tempMax
//                    findViewById<TextView>(R.id.sunrise).text =
//                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
//                    findViewById<TextView>(R.id.sunset).text =
//                        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
//                    findViewById<TextView>(R.id.wind).text = windSpeed
//                    findViewById<TextView>(R.id.pressure).text = pressure
//                    findViewById<TextView>(R.id.humidity).text = humidity
//
//                    /* Views populated, Hiding the loader, Showing the main design */
//                    findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
//                    findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
//
//                }
//            }
        }


    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {

            var response: String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$cityName&units=metric&appid=$apiKey").readText(
                        Charsets.UTF_8
                    )
            } catch (e: Exception) {
                response = null
            }

            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt: Long = jsonObj.getLong("dt")
                val updatedAtText =
                    "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                        Date(updatedAt * 1000)
                    )
                val temp = main.getString("temp") + "°C"
                val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name") + ", " + sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text = updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunrise).text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                findViewById<TextView>(R.id.sunset).text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        }
    }
}