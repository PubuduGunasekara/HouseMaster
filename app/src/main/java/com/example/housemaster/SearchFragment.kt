package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.example.housemaster.databinding.FragmentSettingsBinding
import com.example.housemaster.databinding.FragmentTermsBinding

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var recyclerView: RecyclerView? = null
    private var movieRecyclerViewAdapter: SearchCategoryAdapter? = null
    private var movieList = ArrayList<ServiceCategoryModel>()
    private lateinit var searchBinding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBinding = FragmentSearchBinding.bind(view)
        recyclerView = searchBinding.rvMovieLists
        movieRecyclerViewAdapter = SearchCategoryAdapter(movieList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = movieRecyclerViewAdapter

        prepareMovieListData()

    }

    private fun prepareMovieListData() {
        var movie = ServiceCategoryModel("Avatar", R.drawable.avatar)
        movieList.add(movie)
        movie = ServiceCategoryModel("Batman", R.drawable.batman)
        movieList.add(movie)

        movie = ServiceCategoryModel("End Game", R.drawable.end_game)
        movieList.add(movie)
        movie = ServiceCategoryModel("Hulk", R.drawable.hulk)
        movieList.add(movie)
        movie = ServiceCategoryModel("Inception", R.drawable.inception)
        movieList.add(movie)
        movie = ServiceCategoryModel("Jumanji", R.drawable.jumanji)
        movieList.add(movie)
        movie = ServiceCategoryModel("Lucy", R.drawable.lucy)
        movieList.add(movie)
        movie = ServiceCategoryModel("Jurassic World", R.drawable.jurassic_world)
        movieList.add(movie)
        movie = ServiceCategoryModel("Spider Man", R.drawable.spider_man)
        movieList.add(movie)
        movie = ServiceCategoryModel("Venom", R.drawable.venom)
        movieList.add(movie)

        movieRecyclerViewAdapter!!.notifyDataSetChanged()
    }

}