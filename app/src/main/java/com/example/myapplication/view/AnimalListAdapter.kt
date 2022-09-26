package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemAnimalListBinding
import com.example.myapplication.model.Animal
import com.example.myapplication.util.getProgressDrawable
import com.example.myapplication.util.loadImage

class AnimalListAdapter(private var animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalAdapterViewHolder>() {

    fun updateList(list: List<Animal>){
        animalList.clear()
        animalList.addAll(list)
        notifyDataSetChanged()

    }

    class AnimalAdapterViewHolder(var view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val view = inflater.inflate(R.layout.item_animal_list,parent,false)
        return AnimalAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalAdapterViewHolder, position: Int) {
        val binding = ItemAnimalListBinding.bind(holder.view)
        binding.animalName.text = animalList[position].name
        binding.animalImage.loadImage(animalList[position].imageUri, getProgressDrawable(holder.view.context))
        binding.parentLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(animalList[position])
            Navigation.findNavController(holder.view).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return animalList.size;
    }
}