package com.example.housemaster

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentLoginBinding
import com.example.housemaster.databinding.FragmentReviewBeforeConfirmAppBinding
import com.example.housemaster.databinding.FragmentWelcomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ReviewBeforeConAppFragment : Fragment(R.layout.fragment_review_before_confirm_app) {
    private lateinit var reviewBeforeConfirmAppBinding: FragmentReviewBeforeConfirmAppBinding
    private val args: ReviewBeforeConAppFragmentArgs by navArgs()

    private var serviceTypeSharedPre: String? = null
    private var spNameSharedPre: String? = null
    private var spServiceCategorySharedPre: String? = null
    private var spTypePriceSharedPre: Float? = null
    private var serviceProviderIdSharedPre: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var dbDataRetreive = Firebase.firestore
    private lateinit var authId: String

    private var userDetails: UserProfileModel? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        reviewBeforeConfirmAppBinding = FragmentReviewBeforeConfirmAppBinding.bind(view)

        //shared preference
        sharedPreferences = requireContext().getSharedPreferences(
            "com.example.housemaster",
            Context.MODE_PRIVATE
        )
        serviceProviderIdSharedPre = sharedPreferences.getString("service_provider_id", "")
        serviceTypeSharedPre = sharedPreferences.getString("service_type_title", "")
        spNameSharedPre = sharedPreferences.getString("service_provider_name", "")
        spServiceCategorySharedPre = sharedPreferences.getString("service_item_category", "")
        spTypePriceSharedPre = sharedPreferences.getFloat("service_type_price", 1.0F)
        if (serviceTypeSharedPre != null) {
            /* reviewBeforeConfirmAppBinding.temp.text =
                 "" + args.isHomeService + "" + args.appDate + "" + args.appServiceType + "" + args.appTimeSlot + "" + serviceTypeSharedPre*/
        }



        getUserFromFirebase()
        getCardDetailsFromFirebase()





        reviewBeforeConfirmAppBinding.reviewSpTitle.setText(spNameSharedPre)
        reviewBeforeConfirmAppBinding.reviewSpServiceType.setText(args.appServiceType)
        reviewBeforeConfirmAppBinding.reviewApptDate.setText(args.appDate)
        reviewBeforeConfirmAppBinding.reviewApptTime.setText(args.appTimeSlot)

        reviewBeforeConfirmAppBinding.reviewBillAmount.setText(spTypePriceSharedPre.toString())
        val hst = (spTypePriceSharedPre?.toDouble() ?: 1.0) * 0.13
        reviewBeforeConfirmAppBinding.reviewBillHstAmount.setText(hst.toString())

        reviewBeforeConfirmAppBinding.reviewBillTotal.setText(
            ((spTypePriceSharedPre?.toDouble() ?: 1.0) + hst.toDouble()).toString()
        )


        reviewBeforeConfirmAppBinding.btnConfirmAndPay.setOnClickListener {

            val spName = reviewBeforeConfirmAppBinding.reviewSpTitle.text
            val serviceType = reviewBeforeConfirmAppBinding.reviewSpServiceType.text
            val date = reviewBeforeConfirmAppBinding.reviewApptDate.text
            val time = reviewBeforeConfirmAppBinding.reviewApptTime.text

            val cusName = reviewBeforeConfirmAppBinding.reviewBillCusName.text
            val cusBillAddress = reviewBeforeConfirmAppBinding.reviewBillCusAddress.text
            val amount = reviewBeforeConfirmAppBinding.reviewBillAmount.text
            val hst = reviewBeforeConfirmAppBinding.reviewBillHstAmount.text
            val total = reviewBeforeConfirmAppBinding.reviewBillTotal.text

            val apointmentMap = hashMapOf(
                "userId" to firebaseAuth.currentUser!!.uid,
                "ServiceProviderName" to spName,
                "ServiceProviderId" to serviceProviderIdSharedPre,
                "serviceType" to serviceType,
                "apptDate" to date,
                "aptTime" to time,
                "cusName" to cusName,
                "cusBillAddress" to cusBillAddress,
                "ServiceCategory" to spServiceCategorySharedPre,
                "amount" to amount,
                "hst" to hst,
                "total" to total,
                "aptDone" to false
            )

            dbDataRetreive.collection("appointments").document().set(apointmentMap)
                .addOnSuccessListener {
                    MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                        .setCancelable(false)
                        .setMessage("You have successfully booked the appointment")
                        .setPositiveButton("Done") { dialog_, which ->
                            val action =
                                ReviewBeforeConAppFragmentDirections.actionReviewBeforeConAppFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }.show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()

                }
            /*val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                 findNavController().navigate(action)*/
        }

        //change billing address
        /* reviewBeforeConfirmAppBinding.epChangeBillingAddress.setOnClickListener {
             val action =
                 ReviewBeforeConAppFragmentDirections.actionReviewBeforeConAppFragmentToAddCardsAndAccountsFromRBCFragment()
             findNavController().navigate(action)
         }
 */

    }

    private fun getUserFromFirebase() {
        authId = firebaseAuth.currentUser!!.uid
        database = Firebase.database.reference.child("User")
            .child(authId)

        database.get().addOnSuccessListener {
            if (it.exists()) {
                var userId = it.child("userId").value.toString()
                var fName = it.child("fname").value.toString()
                var lName = it.child("lname").value.toString()
                var email = it.child("email").value.toString()
                var mobile = it.child("mobile").value.toString()
                var province = it.child("province").value.toString()
                var streetAddress = it.child("streetAddress").value.toString()
                var suiteAptNo = it.child("suiteAptNo").value.toString()
                var city = it.child("city").value.toString()
                var postalCode = it.child("postalCode").value.toString()

                userDetails =
                    UserProfileModel(
                        userId,
                        fName,
                        lName,
                        email,
                        mobile,
                        streetAddress,
                        province,
                        suiteAptNo,
                        city,
                        postalCode
                    )

                reviewBeforeConfirmAppBinding.reviewBillCusName.setText(
                    (userDetails?.fName ?: "") + " " + (userDetails?.lName
                        ?: "")
                )

                reviewBeforeConfirmAppBinding.reviewBillCusAddress.setText(
                    (userDetails?.suiteAptNo ?: "") + "-" + (userDetails?.streetAddress
                        ?: "") + "\n" + (userDetails?.city ?: "") + "\n" + (userDetails?.postalCode
                        ?: "") + " " + (userDetails?.province
                        ?: "")
                )


            } else {


            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }


    private fun getCardDetailsFromFirebase() {

        val collectionName = "CustomerCardAccountDetails"
        val userId = firebaseAuth.currentUser!!.uid

        // Create a reference to the document using the email as the document ID
        val documentRef: DocumentReference =
            firebaseFirestore.collection(collectionName).document(userId)

        documentRef.get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot? ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Document exists, retrieve data
                    val documentData = documentSnapshot.data
                    // Process the retrieved data as needed
                    var cardNumber = documentData?.get("cardNumber").toString()
                    var expMnth = documentData?.get("expMonth").toString()
                    var expYear = documentData?.get("expYear").toString()

                    val lastFourDigits = cardNumber.takeLast(4)
                    val cardNumToShow = "**** **** **** $lastFourDigits"
                    reviewBeforeConfirmAppBinding.rbcCardNumber.setText(cardNumToShow)
                    if (expMnth == "January") {
                        expMnth = "01"
                    } else if (expMnth == "February") {
                        expMnth = "02"
                    } else if (expMnth == "March") {
                        expMnth = "03"
                    } else if (expMnth == "April") {
                        expMnth = "04"
                    } else if (expMnth == "May") {
                        expMnth = "05"
                    } else if (expMnth == "June") {
                        expMnth = "06"
                    } else if (expMnth == "July") {
                        expMnth = "07"
                    } else if (expMnth == "August") {
                        expMnth = "08"
                    } else if (expMnth == "September") {
                        expMnth = "09"
                    } else if (expMnth == "October") {
                        expMnth = "10"
                    } else if (expMnth == "November") {
                        expMnth = "11"
                    } else if (expMnth == "December") {
                        expMnth = "12"
                    }
                    reviewBeforeConfirmAppBinding.rbcCardExpireDate.setText(
                        "Valid through: " + expMnth + "/" + expYear.takeLast(
                            2
                        )
                    )
                } else {
                    // Document doesn't exist
                    // Handle the case where the document is not found
                }
            }
            .addOnFailureListener { exception: Exception ->
                // Handle errors
            }
    }


}