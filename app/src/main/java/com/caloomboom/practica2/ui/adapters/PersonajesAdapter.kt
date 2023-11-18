package com.caloomboom.practica2.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caloomboom.practica2.databinding.PersonajeElementBinding
import com.caloomboom.practica2.model.Personajes
import com.caloomboom.practica2.utils.Constants
import java.util.ArrayList

class PersonajesAdapter (private var personajes: ArrayList<Personajes>):RecyclerView.Adapter<PersonajesAdapter.ViewHolder>(){

    class ViewHolder(private var binding: PersonajeElementBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(personajes:Personajes){
            binding.nameCharacter.text=personajes.fullName
            binding.title.text=personajes.title
            Log.d(Constants.LOGTAG,"aQIJDKS ${personajes.imageUrl}")
            Glide.with(itemView.context)
                .load(personajes.imageUrl)
                .into(binding.imagenPersonaje)
            //Glide.with(itemView.context).load(personajes.imageUrl).into(binding.imagenPerfil)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=PersonajeElementBinding.inflate((LayoutInflater.from(parent.context)))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = personajes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personaje= personajes[position]
        holder.bind(personaje)
    }
}