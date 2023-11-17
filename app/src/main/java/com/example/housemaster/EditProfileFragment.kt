package com.example.housemaster

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentEditProfileBinding
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentProfileBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.example.housemaster.databinding.FragmentSettingsBinding
import com.example.housemaster.databinding.FragmentTermsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var editProfileBinding: FragmentEditProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String
    private var userDetails: UserProfileModel? = null
    private var flag: Boolean = false

    //Image picking
    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfileBinding = FragmentEditProfileBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        getUserFromFirebase()

        //Image picking
        editProfileBinding.cImageEp.setOnClickListener {
            pickImageGallery()
        }

        editProfileBinding.epChangePw.setOnClickListener {
            val action =
                EditProfileFragmentDirections.actionEditProfileFragmentToProfileChangePasswordFragment()
            findNavController().navigate(action)
        }







        editProfileBinding.eprofileBtn.setOnClickListener {
            val fName = editProfileBinding.eprofileFname.text.toString()
            val lName = editProfileBinding.eprofileLname.text.toString()
            val email = editProfileBinding.eprofileEmail.text.toString()
            val phone = editProfileBinding.eprofilePhone.text.toString()
            val address1 = editProfileBinding.eprofileAddress1.text.toString()
            val address2 = editProfileBinding.eprofileAddress2.text.toString()

            if (validateEmail() && validateFirstName() && validateLastName() && validateAddress1() && validatePhone()) {


                if (auth.currentUser != null && flag == true) {

                    //create a record
                    authId = auth.currentUser!!.uid
                    userDetails =
                        UserProfileModel(authId, fName, lName, email, phone, address1, address2)
                    database = Firebase.database.reference.child("User")
                        .child(authId)

                    database.setValue(userDetails)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                                    .setCancelable(false)
                                    .setMessage("Save changes successfully")
                                    .setPositiveButton("Done") { dialog_, which ->

                                        val action =
                                            EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
                                        findNavController().navigate(action)


                                    }.show()


                                editProfileBinding.eprofileFname.text?.clear()
                                editProfileBinding.eprofileLname.text?.clear()
                                editProfileBinding.eprofileEmail.text?.clear()
                                editProfileBinding.eprofilePhone.text?.clear()
                                editProfileBinding.eprofileAddress1.text?.clear()
                                editProfileBinding.eprofileAddress2.text?.clear()


                            } else {
                                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }


                } else {

                    //update a record
                    val map = mapOf(
                        "fname" to fName,
                        "lname" to lName,
                        "mobile" to phone,
                        "addressOne" to address1,
                        "addressTwo" to address2,
                        "email" to email
                    )
                    database.updateChildren(map).addOnCompleteListener {
                        if (it.isSuccessful) {
                            MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                                .setCancelable(false)
                                .setMessage("Save changes successfully")
                                .setPositiveButton("Done") { dialog_, which ->

                                    val action =
                                        EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
                                    findNavController().navigate(action)


                                }.show()

                            editProfileBinding.eprofileFname.text?.clear()
                            editProfileBinding.eprofileLname.text?.clear()
                            editProfileBinding.eprofileEmail.text?.clear()
                            editProfileBinding.eprofilePhone.text?.clear()
                            editProfileBinding.eprofileAddress1.text?.clear()
                            editProfileBinding.eprofileAddress2.text?.clear()
                        } else {
                            Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }


            } else {
                validateEmail()
                validateFirstName()
                validateLastName()
                validateAddress1()
                validatePhone()
            }
        }


    }

    private fun getUserFromFirebase() {
        authId = auth.currentUser!!.uid
        database = Firebase.database.reference.child("User")
            .child(authId)

        database.get().addOnSuccessListener {
            if (it.exists()) {
                flag = true
                var userId = it.child("userId").value.toString()
                var fName = it.child("fname").value.toString()
                var lName = it.child("lname").value.toString()
                var email = it.child("email").value.toString()
                var mobile = it.child("mobile").value.toString()
                var addressOne = it.child("addressOne").value.toString()
                var addressTwo = it.child("addressTwo").value.toString()



                editProfileBinding.eprofileFname.setText(fName)
                editProfileBinding.eprofileLname.setText(lName)
                editProfileBinding.eprofileEmail.setText(email)
                editProfileBinding.eprofilePhone.setText(mobile)
                editProfileBinding.eprofileAddress1.setText(addressOne)
                editProfileBinding.eprofileAddress2.setText(addressTwo)


            } else {
                flag = false

            }
        }.addOnFailureListener {
            flag = false
            Log.e("firebase", "Error getting data", it)
        }
    }

    //Image picking
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    //Image picking
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            editProfileBinding.epImage.setImageURI(data?.data)
        }
    }

    private fun validateFirstName(): Boolean {
        var errorMessage: String? = null
        val value: String = editProfileBinding.eprofileFname.text.toString()
        if (value.isEmpty()) {
            errorMessage = "First Name is Required"
        }
        if (errorMessage != null) {
            editProfileBinding.eprofileFnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateLastName(): Boolean {
        var errorMessage: String? = null
        val value: String = editProfileBinding.eprofileLname.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Last Name is Required"
        }

        if (errorMessage != null) {
            editProfileBinding.eprofileLnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    //validate Email
    private fun validateEmail(): Boolean {
        var errorMessage: String? = null
        val value: String = editProfileBinding.eprofileEmail.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"
        }

        if (errorMessage != null) {
            editProfileBinding.eprofileEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePhone(): Boolean {
        var errorMessage: String? = null
        val value: String = editProfileBinding.eprofilePhone.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Phone number is required"
        } else if (!Patterns.PHONE.matcher(value).matches()) {
            errorMessage = "Invalid Phone Number"
        }

        if (errorMessage != null) {
            editProfileBinding.eprofilePhoneTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateAddress1(): Boolean {
        var errorMessage: String? = null
        val value: String = editProfileBinding.eprofileAddress1.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Address line 1 is required"
        }

        if (errorMessage != null) {
            editProfileBinding.eprofileAddress1Til.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }


}