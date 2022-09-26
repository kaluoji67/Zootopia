package com.example.myapplication.view

import android.graphics.Path
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding.inflate
import com.example.myapplication.databinding.FragmentDetailsBinding.bind
import com.example.myapplication.databinding.FragmentDetailsBinding.inflate
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.model.Animal
import com.example.myapplication.viewmodel.ListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    private lateinit var viewModel : ListViewModel
    private lateinit var animalList : RecyclerView
    private lateinit var listErrorMessage : TextView
    private lateinit var listProgressBar : ProgressBar

    val animalListAdapter = AnimalListAdapter(arrayListOf())

    //observers
    val animalListLiveDataObserver = Observer<List<Animal>>{ list->
        list?.let{
            animalList.visibility= View.VISIBLE
            animalListAdapter.updateList(list)
        }
    }
    val loadErrorLiveDataObserver = Observer<Boolean>{ isError->
        listErrorMessage.visibility = if(isError) View.VISIBLE else View.GONE
        listErrorMessage.text = ""
    }
    val isLoadingLiveDataObserver = Observer<Boolean>{ loading->
        listProgressBar.visibility = if(loading) View.VISIBLE else View.GONE
        if(loading){
            animalList.visibility= View.GONE
            listErrorMessage.visibility = View.GONE
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.animals.observe(viewLifecycleOwner,animalListLiveDataObserver)
        viewModel.loading.observe(viewLifecycleOwner,isLoadingLiveDataObserver)
        viewModel.loadError.observe(viewLifecycleOwner,loadErrorLiveDataObserver)

        viewModel.refresh()

        val binding = FragmentListBinding.bind(view)
        animalList = binding.animalList
        listErrorMessage = binding.listError
        listProgressBar = binding.progressBar

        animalList.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = animalListAdapter
        }

        binding.refreshLayout.setOnRefreshListener {
            animalList.visibility= View.GONE
            listErrorMessage.visibility = View.GONE
            listProgressBar.visibility = View.GONE

            viewModel.refresh();
            binding.refreshLayout.isRefreshing=false;
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}