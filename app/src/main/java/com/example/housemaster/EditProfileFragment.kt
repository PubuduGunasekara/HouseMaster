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
        editProfileBinding.loadingSpinner.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance()
        getUserFromFirebase()

        //Image picking
        /*editProfileBinding.cImageEp.setOnClickListener {
            pickImageGallery()
        }*/

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
            val streetAddress = editProfileBinding.eprofileStreetAddress.text.toString()
            val suiteAptNo = editProfileBinding.eprofileAptSuiteNumber.text.toString()
            val province = "Ontario"
            val city = editProfileBinding.eprofileCity.text.toString()
            val postalCode = editProfileBinding.eprofilePostalCode.text.toString()

            if (validateEmail(editProfileBinding.eprofileEmail.text.toString()) && validateFirstName(
                    editProfileBinding.eprofileFname.text.toString()
                ) && validateLastName(editProfileBinding.eprofileLname.text.toString()) && validateStreetAddress(
                    editProfileBinding.eprofileStreetAddress.text.toString()
                ) && validatePhone(editProfileBinding.eprofilePhone.text.toString()) && validateSuiteAptNumber(
                    editProfileBinding.eprofileAptSuiteNumber.text.toString()
                ) && validateCity(editProfileBinding.eprofileCity.text.toString()) && validatePostalCode(
                    editProfileBinding.eprofilePostalCode.text.toString()
                )
            ) {


                if (auth.currentUser != null && flag == true) {

                    //create a record
                    authId = auth.currentUser!!.uid
                    userDetails =
                        UserProfileModel(
                            authId,
                            fName,
                            lName,
                            email,
                            phone,
                            streetAddress,
                            province,
                            suiteAptNo,
                            city,
                            postalCode
                        )
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

                                editProfileBinding.eprofileStreetAddress.text?.clear()

                                editProfileBinding.eprofileAptSuiteNumber.text?.clear()

                                editProfileBinding.eprofileCity.text?.clear()
                                editProfileBinding.eprofilePostalCode.text?.clear()


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
                        "streetAddress" to streetAddress,
                        "suiteAptNo" to suiteAptNo,
                        "province" to province,
                        "city" to city,
                        "postalCode" to postalCode,
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

                            editProfileBinding.eprofileStreetAddress.text?.clear()

                            editProfileBinding.eprofileAptSuiteNumber.text?.clear()

                            editProfileBinding.eprofileCity.text?.clear()
                            editProfileBinding.eprofilePostalCode.text?.clear()
                        } else {
                            Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }


            } else {
                validateEmail(editProfileBinding.eprofileEmail.text.toString())
                validateFirstName(editProfileBinding.eprofileFname.text.toString())
                validateLastName(editProfileBinding.eprofileLname.text.toString())
                validateStreetAddress(editProfileBinding.eprofileStreetAddress.text.toString())
                validateCity(editProfileBinding.eprofileCity.text.toString())
                validateSuiteAptNumber(editProfileBinding.eprofileAptSuiteNumber.text.toString())
                validatePostalCode(editProfileBinding.eprofilePostalCode.text.toString())
                validatePhone(editProfileBinding.eprofilePhone.text.toString())
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
                var streetAddress = it.child("streetAddress").value.toString()
                var suiteAptNo = it.child("suiteAptNo").value.toString()
                var city = it.child("city").value.toString()
                var postalCode = it.child("postalCode").value.toString()





                editProfileBinding.eprofileFname.setText(fName)
                editProfileBinding.eprofileLname.setText(lName)
                editProfileBinding.eprofileEmail.setText(email)
                editProfileBinding.eprofilePhone.setText(mobile)
                editProfileBinding.eprofileStreetAddress.setText(streetAddress)
                editProfileBinding.eprofileCity.setText(city)
                editProfileBinding.eprofilePostalCode.setText(postalCode)
                editProfileBinding.eprofileAptSuiteNumber.setText(suiteAptNo)
                editProfileBinding.loadingSpinner.setVisibility(View.GONE);

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
            //editProfileBinding.epImage.setImageURI(data?.data)
        }
    }

    /*
        public fun validateFirstName(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "First Name is Required"

                return false
            }

            if (errorMessage != null) {
                editProfileBinding.eprofileFnameTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }

            return true
        }

        public fun validateLastName(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Last Name is Required"

                return false
            }
            if (errorMessage != null) {
                editProfileBinding.eprofileLnameTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }


            return true
        }

        //validate Email
        public fun validateEmail(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Email is required"

                return false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                errorMessage = "Invalid Email Address"

                return false
            }
            if (errorMessage != null) {
                editProfileBinding.eprofileEmailTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }

            return true
        }

        public fun validatePhone(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Phone number is required"
                return false
            } else if (!Patterns.PHONE.matcher(value).matches()) {
                errorMessage = "Invalid Phone Number"

                return false
            }

            if (errorMessage != null) {
                editProfileBinding.eprofilePhoneTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }

            return true
        }

        public fun validateStreetAddress(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Street Address is required"

                return false
            }

            if (errorMessage != null) {
                editProfileBinding.eprofileStreetAddressTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }
            return true
        }

        public fun validateSuiteAptNumber(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Suite/Apt # is required"

                return false
            }

            if (errorMessage != null) {
                editProfileBinding.eprofileAptSuiteNumberTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }

            return true
        }

        private fun validateProvince(): Boolean {
            var errorMessage: String? = null
            val value: String = editProfileBinding.eprofileProvince.text.toString()
            if (value.isEmpty()) {
                errorMessage = "Province is required"
                return false
            }

            if (errorMessage != null) {
                editProfileBinding.eprofileProvinceTil.apply {
                    isErrorEnabled = true
                    error = errorMessage

                }
            }
            return errorMessage == null
        }

        public fun validateCity(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "City is required"

                return false
            }
            if (errorMessage != null) {
                editProfileBinding.eprofileCityTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }
            return true
        }

        public fun validatePostalCode(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Postal Code is required"

                return false
            }

            if (errorMessage != null) {
                editProfileBinding.eprofilePostalCodeTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }
            return true
        }

    */


    public fun validateFirstName(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "First Name is Required"
            editProfileBinding.eprofileFnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    public fun validateLastName(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Last Name is Required"
            editProfileBinding.eprofileLnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    //validate Email
    public fun validateEmail(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Email is required"
            editProfileBinding.eprofileEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"
            editProfileBinding.eprofileEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }


        return true
    }

    public fun validatePhone(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Phone number is required"
            return false
        } else if (!Patterns.PHONE.matcher(value).matches()) {
            errorMessage = "Invalid Phone Number"
            editProfileBinding.eprofilePhoneTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    public fun validateStreetAddress(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Street Address is required"
            editProfileBinding.eprofileStreetAddressTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }
        return true
    }

    public fun validateSuiteAptNumber(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Suite/Apt # is required"
            editProfileBinding.eprofileAptSuiteNumberTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    private fun validateProvince(): Boolean {
        var errorMessage: String? = null
        val value: String = editProfileBinding.eprofileProvince.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Province is required"
            editProfileBinding.eprofileProvinceTil.apply {
                isErrorEnabled = true
                error = errorMessage
                return false
            }
        }
        return errorMessage == null
    }

    public fun validateCity(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "City is required"
            editProfileBinding.eprofileCityTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    public fun validatePostalCode(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Postal Code is required"
            editProfileBinding.eprofilePostalCodeTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }
        return true
    }


}