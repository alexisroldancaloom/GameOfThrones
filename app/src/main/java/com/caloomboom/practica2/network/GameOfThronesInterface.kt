import com.caloomboom.practica2.model.Personaje
import com.caloomboom.practica2.model.Personajes

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface GameOfThronesInterface {
    @GET("api/v2/Characters")
    fun getPersonajes(): Call<List<Personajes>>

    @GET("api/v2/Characters/{id}")
    fun getPersonaje(@Path("id") id: Int): Call<Personaje>
}