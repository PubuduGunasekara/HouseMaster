package com.example.housemaster

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentAppointmentHistoryBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentHistoryFragment : Fragment(R.layout.fragment_appointment_history) {
    //upcoming apt
    private var recyclerViewUpcoming: RecyclerView? = null
    private var aptListUpcomingRecyclerViewAdapter: AppointmentHistoryAdapter? = null
    private var aptListUpcoming = ArrayList<AppointmentListModel>()

    //prev apt
    private var recyclerViewPrev: RecyclerView? = null
    private var aptListPrevRecyclerViewAdapter: AppointmentHistoryAdapter? = null
    private var aptListPrev = ArrayList<AppointmentListModel>()


    private lateinit var aptHistoryBinding: FragmentAppointmentHistoryBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        (activity as AppCompatActivity).supportActionBar?.title = "Appointment History"

        if (firebaseAuth.currentUser == null) {
            val action = HomeFragmentDirections.actionHomeFragmentToSignInFragment()
            findNavController().navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)
        aptHistoryBinding = FragmentAppointmentHistoryBinding.bind(view)
        getUpcomingAptData()
        getPrevAptData()

    }

    private fun getUpcomingAptData() {

        recyclerViewUpcoming = aptHistoryBinding.rvUpcomingAppointments
        recyclerViewUpcoming?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewUpcoming!!.setHasFixedSize(true)

        aptListUpcoming = arrayListOf<AppointmentListModel>()


        val ref = firebaseFirestore.collection("appointments")
        ref.whereEqualTo("aptDone", false)
            .get().addOnSuccessListener { documentSnapshot ->
                if (!documentSnapshot.isEmpty) {
                    aptHistoryBinding.llUpcomingAptNoData.isGone = true
                    val documents = documentSnapshot.documents
                    for (sptData in documents) {
                        val data = AppointmentListModel(
                            sptData.id,
                            sptData["ServiceProviderName"].toString(),
                            sptData["apptDate"].toString(),
                            sptData["ServiceProviderName"].toString(),
                        )
                        aptListUpcoming.add(data)

                        var adapter2 = AppointmentHistoryAdapter(aptListUpcoming)
                        recyclerViewUpcoming!!.adapter = adapter2
                        adapter2.setOnItemClickListener(object :
                            AppointmentHistoryAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                val spId = aptListUpcoming[position].aptId
                                val action =
                                    AppointmentHistoryFragmentDirections.actionAppointmentHistoryFragmentToAppointmentItemFragment(
                                        spId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                        )
                    }
                } else {
                    aptHistoryBinding.llUpcomingAptNoData.isGone = false
                }

            }.addOnFailureListener {

                Log.e("firebase", "Error getting data", it)
            }

    }

    private fun getPrevAptData() {

        recyclerViewPrev = aptHistoryBinding.rvPrevAppointments
        recyclerViewPrev?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewPrev!!.setHasFixedSize(true)

        aptListPrev = arrayListOf<AppointmentListModel>()


        val ref = firebaseFirestore.collection("appointments")
        ref.whereEqualTo("aptDone", true)
            .get().addOnSuccessListener { documentSnapshot ->
                if (!documentSnapshot.isEmpty) {

                    aptHistoryBinding.llPrevAptNoData.isGone = true
                    val documents = documentSnapshot.documents
                    for (sptData in documents) {
                        val data = AppointmentListModel(
                            sptData.id,
                            sptData["ServiceProviderName"].toString(),
                            sptData["apptDate"].toString(),
                            sptData["ServiceProviderName"].toString(),
                        )
                        aptListPrev.add(data)

                        var adapter2 = AppointmentHistoryAdapter(aptListPrev)
                        recyclerViewPrev!!.adapter = adapter2
                        adapter2.setOnItemClickListener(object :
                            AppointmentHistoryAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                val spId = aptListPrev[position].aptId
                                val action =
                                    AppointmentHistoryFragmentDirections.actionAppointmentHistoryFragmentToAppointmentItemFragment(
                                        spId
                                    )
                                findNavController().navigate(action)
                            }
                        }
                        )
                    }
                } else {
                    aptHistoryBinding.llPrevAptNoData.isGone = false
                }

            }.addOnFailureListener {

                Log.e("firebase", "Error getting data", it)
            }

    }
}