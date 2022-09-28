package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemAnimalListBinding
import com.example.myapplication.model.Animal
import com.example.myapplication.util.getProgressDrawable
import com.example.myapplication.util.loadImage

class AnimalListAdapter(private var animalList: ArrayList<Animal>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalAdapterViewHolder>(), AnimalListClickListener {

    fun updateList(list: List<Animal>){
        animalList.clear()
        animalList.addAll(list)
        notifyDataSetChanged()

    }

    class AnimalAdapterViewHolder(var view : ItemAnimalListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val view = DataBindingUtil.inflate<ItemAnimalListBinding>(inflater,R.layout.item_animal_list,parent,false)
        return AnimalAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalAdapterViewHolder, position: Int) {

        holder.view.animal = animalList[position]
        holder.view.listener = this
//        binding.parentLayout.setOnClickListener {
//            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(animalList[position])
//            Navigation.findNavController(holder.view).navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return animalList.size;
    }

    override fun onClick(v: View) {
        val name = v.tag.toString()
        lateinit var animal : Animal
        for(anim in animalList){
            if(name==anim.name)
                animal=anim
        }
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment(animal)
        Navigation.findNavController(v).navigate(action)
    }
}