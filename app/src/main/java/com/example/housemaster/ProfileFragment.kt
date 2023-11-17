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


        profileBinding.profileEditBtn.setOnClickListener {
             val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
             findNavController().navigate(action)
         }



        getUserFromFirebase()


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
                var addressOne = it.child("addressOne").value.toString()
                var addressTwo = it.child("addressTwo").value.toString()

                userDetails =
                    UserProfileModel(userId, fName, lName, email, mobile, addressOne, addressTwo)

                profileBinding.profileFName.text = fName
                profileBinding.profileLName.text = lName
                profileBinding.profileEmail.text = email
                profileBinding.profilePhone.text = mobile
                profileBinding.address1.text = addressOne
                profileBinding.address2.text = addressTwo


            } else {
                profileBinding.pImage.visibility = View.GONE
                profileBinding.profileFName.visibility = View.GONE
                profileBinding.profileLName.visibility = View.GONE
                profileBinding.profileEmail.visibility = View.GONE
                profileBinding.profilePhone.visibility = View.GONE
                profileBinding.address1.visibility = View.GONE
                profileBinding.address2.visibility = View.GONE

                profileBinding.profileFNameIcon.visibility = View.GONE
                profileBinding.profileLNameIcon.visibility = View.GONE
                profileBinding.profileEmailIcon.visibility = View.GONE
                profileBinding.profilePhoneIcon.visibility = View.GONE
                profileBinding.profileAddress1Icon.visibility = View.GONE
                profileBinding.profileAddress2Icon.visibility = View.GONE

            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }
}