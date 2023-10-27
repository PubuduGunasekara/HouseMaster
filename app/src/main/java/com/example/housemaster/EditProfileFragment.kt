package com.example.housemaster

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
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

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var editProfileBinding: FragmentEditProfileBinding

    //Image picking
    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfileBinding = FragmentEditProfileBinding.bind(view)

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

                MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                    .setCancelable(false)
                    .setMessage("Save changes successfully")
                    .setPositiveButton("Done") { dialog_, which ->

                        val action =
                            EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
                        findNavController().navigate(action)


                    }.show()


            } else {
                validateEmail()
                validateFirstName()
                validateLastName()
                validateAddress1()
                validatePhone()
            }
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