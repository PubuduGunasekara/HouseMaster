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

        var adapter = movieRecyclerViewAdapter
        adapter?.setOnItemClickListener(object : SearchCategoryAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val categoryId = movieList[position].categoryId
                val categoryTitle = movieList[position].categoryName
                val action =
                    SearchFragmentDirections.actionSearchFragmentToIndividualCategorySPListFragment(
                        categoryId,
                        categoryTitle
                    )
                findNavController().navigate(action)
            }
        }
        )

    }

    private fun prepareMovieListData() {
        var movie = ServiceCategoryModel("cate01", "Avatar", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate02", "Batman", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)

        movie = ServiceCategoryModel("cate03", "End Game", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate04", "Hulk", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate05", "Inception", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate06", "Jumanji", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate07", "Lucy", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate08", "Jurassic World", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate09", "Spider Man", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)
        movie = ServiceCategoryModel("cate10", "Venom", "https://firebasestorage.googleapis.com/v0/b/housemaster-2020b.appspot.com/o/meetpedhadiya12%40gmail.com.png?alt=media&token=a0776671-fd57-4fe2-bbf8-4b99a3419789")
        movieList.add(movie)

        movieRecyclerViewAdapter!!.notifyDataSetChanged()
    }

}