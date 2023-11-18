package com.caloomboom.practica2.ui.activities

import GameOfThronesInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caloomboom.practica2.databinding.ActivityMainBinding
import com.caloomboom.practica2.model.Personajes
import com.caloomboom.practica2.network.RetrofitService
import com.caloomboom.practica2.ui.adapters.PersonajesAdapter
import com.caloomboom.practica2.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = RetrofitService.getRetrofit().create(GameOfThronesInterface::class.java).getPersonajes()

        call.enqueue(object : Callback<List<Personajes>>{
            override fun onResponse(
                call: Call<List<Personajes>>,
                response: Response<List<Personajes>>
            ) {
                binding.progressConexion.visibility= View.INVISIBLE
                Log.d(Constants.LOGTAG, "Respuesta del servicio ${response.toString()}")
                Log.d(Constants.LOGTAG,"Datos ${response.body().toString()}")

                val personajesAdapter = PersonajesAdapter(ArrayList(response.body()))
                val layoutManager = LinearLayoutManager(this@MainActivity)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                binding.personajesList.layoutManager = layoutManager

                binding.personajesList.adapter=personajesAdapter
            }

            override fun onFailure(call: Call<List<Personajes>>, t: Throwable) {
                binding.progressConexion.visibility= View.INVISIBLE
                Toast.makeText(this@MainActivity, "No hay dispo", Toast.LENGTH_LONG).show()
            }

        })


    }
}