package com.example.myapplication.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.model.Animal
import com.example.myapplication.util.getProgressDrawable
import com.example.myapplication.util.loadImage
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {
    var animal : Animal?=null
//    lateinit var detailsLayout : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            animal = DetailsFragmentArgs.fromBundle(it).animal
        }

        val binding = FragmentDetailsBinding.bind(view)

//        context?.let {
//            binding.animalImage.loadImage(animal?.imageUri, getProgressDrawable(it))
//            }


//        detailsLayout = binding.detailsLayout


//        animal?.imageUri?.let {
//            setBackground(it)
//        }

        binding.animal = animal
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}