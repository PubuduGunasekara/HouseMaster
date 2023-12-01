package com.example.housemaster

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentAppointmentItemBinding
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.example.housemaster.databinding.FragmentServiceItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class AppointmentItemFragment : Fragment(R.layout.fragment_appointment_item) {

    private lateinit var fragmentApptItemBinding: FragmentAppointmentItemBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val args: AppointmentItemFragmentArgs by navArgs()

    private lateinit var firebaseAuth: FirebaseAuth
    private var documentListener: ListenerRegistration? = null
    private lateinit var database: DatabaseReference
    private var dbDataRetreive = Firebase.firestore
    private lateinit var aptSPId: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        fragmentApptItemBinding = FragmentAppointmentItemBinding.bind(view)

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE
        getAptFromFirebase()

    }


    private fun getAptFromFirebase() {


        val ref = firebaseFirestore.collection("appointments").document(args.aptId)

        documentListener =
            ref.addSnapshotListener { snapshot: DocumentSnapshot?, exception: FirebaseFirestoreException? ->
                if (exception != null) {
                    // Handle errors
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    // Document exists, retrieve data and update TextView
                    val documentData = snapshot.data
                    val documentText = documentData?.get("your_field_name")?.toString()
                    aptSPId = documentData?.get("ServiceProviderId").toString()
                    var spName = documentData?.get("ServiceProviderName").toString()
                    var spCategory = documentData?.get("ServiceCategory").toString()
                    var spType = documentData?.get("serviceType").toString()
                    var aptDate = documentData?.get("apptDate").toString()
                    var aptTimeSlot = documentData?.get("aptTime").toString()
                    var cusName = documentData?.get("cusName").toString()
                    var billingAdd = documentData?.get("cusBillAddress").toString()
                    var amount = documentData?.get("amount").toString()
                    var hst = documentData?.get("hst").toString()
                    var total = documentData?.get("total").toString()

                    //getting the image from users


                    val collectionName = "users"
                    val emailToSearch = aptSPId

                    // Create a reference to the document using the email as the document ID
                    val documentRef: DocumentReference =
                        firebaseFirestore.collection(collectionName).document(emailToSearch)

                    documentRef.get()
                        .addOnSuccessListener { documentSnapshot: DocumentSnapshot? ->
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                // Document exists, retrieve data
                                val documentData = documentSnapshot.data
                                // Process the retrieved data as needed
                                var image = documentData?.get("image").toString()
                                Picasso.get().load(image).into(fragmentApptItemBinding.siCoverImage)

                            } else {
                                // Document doesn't exist
                                // Handle the case where the document is not found
                            }
                        }
                        .addOnFailureListener { exception: Exception ->
                            // Handle errors
                        }


                    // Update TextView
                    fragmentApptItemBinding.siTitle.setText(spName)
                    fragmentApptItemBinding.siCategory.setText(spCategory)
                    fragmentApptItemBinding.reviewSpServiceType.setText(spType)
                    fragmentApptItemBinding.reviewApptDate.setText(aptDate)
                    fragmentApptItemBinding.reviewApptTime.setText(aptTimeSlot)
                    fragmentApptItemBinding.reviewBillCusName.setText(cusName)
                    fragmentApptItemBinding.reviewBillCusAddress.setText(billingAdd)
                    fragmentApptItemBinding.reviewBillAmount.setText(amount)
                    fragmentApptItemBinding.reviewBillHstAmount.setText(hst)
                    fragmentApptItemBinding.reviewBillTotal.setText(total)
                } else {
                    // Document doesn't exist

                }
            }

    }


}