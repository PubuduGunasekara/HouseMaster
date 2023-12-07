package com.example.housemaster

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentProfileBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.example.housemaster.databinding.FragmentSettingsBinding
import com.example.housemaster.databinding.FragmentTermsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var authId: String

    private var userDetails: UserProfileModel? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser == null) {
            val action = HomeFragmentDirections.actionHomeFragmentToSignInFragment()
            findNavController().navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)
        profileBinding = FragmentProfileBinding.bind(view)
        profileBinding.loadingSpinner.setVisibility(View.VISIBLE);

        profileBinding.profileEditBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }



        getUserFromFirebase(firebaseAuth.currentUser!!.uid)


    }


    public fun getUserFromFirebase(authId: String) {

        database = Firebase.database.reference.child("User")
            .child(authId)

        database.get().addOnSuccessListener {

            if (it.exists()) {
                var userId = it.child("userId").value.toString()
                var fName = it.child("fname").value.toString()
                var lName = it.child("lname").value.toString()
                var email = it.child("email").value.toString()
                var mobile = it.child("mobile").value.toString()
                var addressOne = it.child("addressOne").value.toString()
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

                profileBinding.profileFName.text = fName
                profileBinding.profileLName.text = lName
                profileBinding.profileEmail.text = email
                profileBinding.profilePhone.text = mobile
                profileBinding.streetAddress.text = streetAddress
                profileBinding.city.text = city
                profileBinding.postalCode.text = postalCode
                profileBinding.suiteAptNo.text = suiteAptNo
                profileBinding.province.text = province

                profileBinding.loadingSpinner.setVisibility(View.GONE);
            } else {
                // profileBinding.pImage.visibility = View.GONE
                profileBinding.profileFName.visibility = View.GONE
                profileBinding.profileLName.visibility = View.GONE
                profileBinding.profileEmail.visibility = View.GONE
                profileBinding.profilePhone.visibility = View.GONE
                profileBinding.streetAddress.visibility = View.GONE
                profileBinding.city.visibility = View.GONE
                profileBinding.postalCode.visibility = View.GONE
                profileBinding.suiteAptNo.visibility = View.GONE
                profileBinding.province.visibility = View.GONE

                profileBinding.profileFNameIcon.visibility = View.GONE
                profileBinding.profileLNameIcon.visibility = View.GONE
                profileBinding.profileEmailIcon.visibility = View.GONE
                profileBinding.profilePhoneIcon.visibility = View.GONE
                profileBinding.profileStreetAddressIcon.visibility = View.GONE
                profileBinding.profileCityIcon.visibility = View.GONE
                profileBinding.profilePostalCodeIcon.visibility = View.GONE
                profileBinding.profileSuiteAptNoIcon.visibility = View.GONE
                profileBinding.profileProvinceIcon.visibility = View.GONE

            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)

        }
    }
}