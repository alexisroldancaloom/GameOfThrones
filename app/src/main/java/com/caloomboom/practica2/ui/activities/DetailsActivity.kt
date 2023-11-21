package com.caloomboom.practica2.ui.activities

import GameOfThronesInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.caloomboom.practica2.R
import com.caloomboom.practica2.databinding.ActivityDetailsBinding
import com.caloomboom.practica2.model.Personaje
import com.caloomboom.practica2.network.RetrofitService
import com.caloomboom.practica2.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getInt("id", 0) ?: 0

        val call = RetrofitService.getRetrofit()
            .create(GameOfThronesInterface::class.java)
            .getPersonaje(id)

        call.enqueue(object : Callback<Personaje>{
            override fun onResponse(call: Call<Personaje>, response: Response<Personaje>) {
                binding.progressConexion.visibility= View.INVISIBLE
                Log.d(Constants.LOGTAG,"Datos ${response.body().toString()}")

                binding.nombre.text=response.body()?.fullName

                Glide.with(this@DetailsActivity).load(response.body()?.imageUrl).into(binding.imagenPersonaje)

                binding.titulo.text=response.body()?.title
                binding.familia.text=response.body()?.family

                val familyImageMap = mapOf(
                    "House Stark" to R.drawable.stark,
                    "House Targaryen" to R.drawable.targaryen,
                    "House Tarly" to R.drawable.tyrell,
                    "House Baratheon" to R.drawable.baratheon,
                    "House Lannister" to R.drawable.baratheon,
                    "House Greyjoy" to R.drawable.greyjoy,
                    "House Mormont" to R.drawable.mormont,
                    "House Martell" to R.drawable.martell,
                    "House Martell" to R.drawable.martell,
                    "House Bolton" to R.drawable.bolton
                )


                val familyImageResource = familyImageMap[response.body()?.family]

                familyImageResource?.let {
                    Glide.with(this@DetailsActivity)
                        .load(it)
                        .into(binding.imagenFamilia)
                }


            }

            override fun onFailure(call: Call<Personaje>, t: Throwable) {
                binding.progressConexion.visibility= View.INVISIBLE
                Toast.makeText(this@DetailsActivity, "No hay dispo", Toast.LENGTH_LONG).show()
            }

        })
    }
}
