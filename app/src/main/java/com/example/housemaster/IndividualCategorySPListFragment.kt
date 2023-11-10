package com.example.housemaster

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class IndividualCategorySPListFragment : Fragment(R.layout.fragment_individual_category) {

    private lateinit var icViewBinding: FragmentIndividualCategoryBinding
    private val args: IndividualCategorySPListFragmentArgs by navArgs()


    private lateinit var spRecyclerView: RecyclerView
    private lateinit var spArrayList: ArrayList<ServiceProviderModel>
    lateinit var spName: Array<String>
    lateinit var spImage: Array<Int>
    lateinit var spRatings: Array<String>
    lateinit var spId: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        icViewBinding = FragmentIndividualCategoryBinding.bind(view)


        (activity as AppCompatActivity).supportActionBar?.title = args.categoryTitle
        //adapter implementation for service provider list

        spId = arrayOf(
            args.categoryId,
            args.categoryTitle,
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
        )

        spName = arrayOf(
            args.categoryId,
            args.categoryTitle,
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
        )


        spRatings = arrayOf(
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
        )

        spImage = arrayOf(
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,

            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,

            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
        )

        spRecyclerView = icViewBinding.individualCategoryRV
        spRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        spRecyclerView.setHasFixedSize(true)

        spArrayList = arrayListOf<ServiceProviderModel>()
        getSpData()


        var adapter2 = ServiceProviderAdapter(spArrayList)
        spRecyclerView.adapter = adapter2
        adapter2.setOnItemClickListener(object : ServiceProviderAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val spId = spArrayList[position].spId
                val action =
                    IndividualCategorySPListFragmentDirections.actionIndividualCategorySPListFragmentToServiceItemFragment(
                        spId
                    )
                findNavController().navigate(action)
            }
        }
        )

//end of adapter implementation


    }

    private fun getSpData() {
        for (i in spName.indices) {
            val spModelData = ServiceProviderModel(spId[i], spName[i], spImage[i], spRatings[i])
            spArrayList.add(spModelData)
        }
    }
}