package com.example.assignment.ui.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.api.response.bylatlong.Coord
import com.example.assignment.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel by viewModels<DetailViewModel>()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailBinding.bind(view)

        val coord: Coord? = requireArguments().getParcelable<Coord>("location")
        coord?.let {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.callWeatherApiByLocation(it.lat, it.lon)
        }

        viewModel.searchByLatLong.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            Log.d("response by lat lng", "$it")
            if (it.weather.isNotEmpty()) {
                val url: String = getImageUrl(it.weather[0].icon)
                //
                Glide.with(this).load(url).into(binding.imageView);
                Log.d("image url ", "$url")
                var image: Bitmap? = null

                //val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                //StrictMode.setThreadPolicy(policy)
                //GlobalScope.launch {
                //    try {
                //      val url = URL(url)

                //      image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                //      this.launch(Dispatchers.Main){
                //          binding.imageView.setImageBitmap(image)
                //      }
                //  } catch (e: IOException) {
                //      println(e)
                //  }
                //}

            }
            val sb = StringBuilder()
            sb.append("City Name: ").append(it.name).append("\n")
            try {
                sb.append("Weather : ").append(it.weather[0].description).append("\n")
                sb.append("Weather State: ").append(it.weather[0].main).append("\n")
            }catch (e: Exception){e.printStackTrace()}

            sb.append("Humidity: ").append(it.main.humidity).append("\n")
            sb.append("Pressure: ").append(it.main.pressure).append("\n")
            sb.append("Height from Sea Level: ").append(it.main.sea_level).append("\n")
            sb.append("Temperature: ").append(it.main.temp).append("\n")
            sb.append("Min Temperature: ").append(it.main.temp_min).append("\n")
            sb.append("Max Temperature: ").append(it.main.temp_max)

            binding.textviewCreator.text = sb

        }

    }

    private fun getImageUrl(weatherIcon: String): String {
        val placeHolder: String = " https://openweathermap.org/img/wn/"
        val ext: String = "@2x.png"
        return placeHolder+ weatherIcon+ ext
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}