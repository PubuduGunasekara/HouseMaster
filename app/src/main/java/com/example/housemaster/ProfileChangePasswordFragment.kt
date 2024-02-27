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
import com.example.housemaster.databinding.FragmentProfileChangePasswordBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.example.housemaster.databinding.FragmentSettingsBinding
import com.example.housemaster.databinding.FragmentTermsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class ProfileChangePasswordFragment : Fragment(R.layout.fragment_profile_change_password) {

    private lateinit var profileChangePasswordBinding: FragmentProfileChangePasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileChangePasswordBinding = FragmentProfileChangePasswordBinding.bind(view)
        //initiate the firebase object
        firebaseAuth = FirebaseAuth.getInstance()

        profileChangePasswordBinding.epChangePWBtn.setOnClickListener {

            if (validateCurrentPassword(profileChangePasswordBinding.epCurrentPassword.text.toString()) && validateNewPassword(
                    profileChangePasswordBinding.epNewPassword.text.toString()
                ) && validateConPassword(profileChangePasswordBinding.epNewPasswordCon.text.toString())
            ) {


                if (validatePasswordAndConPassword(
                        profileChangePasswordBinding.epNewPassword.text.toString(),
                        profileChangePasswordBinding.epNewPasswordCon.text.toString()
                    )
                ) {

                    val user = firebaseAuth.currentUser
                    val currentPassword =
                        profileChangePasswordBinding.epCurrentPassword.text.toString()
                    val newPassword = profileChangePasswordBinding.epNewPassword.text.toString()
                    val cPassword = profileChangePasswordBinding.epNewPasswordCon.text.toString()


                    firebaseAuth.signInWithEmailAndPassword(user?.email.toString(), currentPassword)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                user?.updatePassword(newPassword)?.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                                            .setCancelable(false)
                                            .setMessage("You have successfully updated the password. Please Sign In again!")
                                            .setPositiveButton("Done") { dialog_, which ->

                                                firebaseAuth.signOut()

                                                val action =
                                                    ProfileChangePasswordFragmentDirections.actionProfileChangePasswordFragmentToSignInFragment()
                                                findNavController().navigate(action)


                                            }.show()
                                    } else {
                                        MaterialAlertDialogBuilder(requireContext()).setTitle("Failed")
                                            .setIcon(R.drawable.baseline_error_24)
                                            .setCancelable(false)
                                            .setMessage(it.exception?.message.toString())
                                            .setPositiveButton("Done") { dialog_, which ->

                                                val action =
                                                    ProfileChangePasswordFragmentDirections.actionProfileChangePasswordFragmentToEditProfileFragment()
                                                findNavController().navigate(action)


                                            }.show()
                                    }
                                }
                            } else {
                                MaterialAlertDialogBuilder(requireContext()).setTitle("Sign In Error")
                                    .setCancelable(false)
                                    .setMessage(it.exception?.message.toString())
                                    .setPositiveButton("Done") { dialog_, which ->
                                        false


                                    }.show()

                            }
                        }

                } else {
                    validatePasswordAndConPassword(
                        profileChangePasswordBinding.epNewPassword.text.toString(),
                        profileChangePasswordBinding.epNewPasswordCon.text.toString()
                    )
                }

            } else {
                validateCurrentPassword(profileChangePasswordBinding.epCurrentPassword.text.toString())
                validateNewPassword(profileChangePasswordBinding.epNewPassword.text.toString())
                validateConPassword(profileChangePasswordBinding.epNewPasswordCon.text.toString())
            }
        }


    }
/*
    public fun validateCurrentPassword(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Current Password is Required"

            return false
        }

        if (errorMessage != null) {
            profileChangePasswordBinding.epCurrentPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

    public fun validateNewPassword(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "New Password is Required"

            return false
        } else if (value.length < 6) {
            errorMessage = "New Password must be six characters long"

            return false
        }
        if (errorMessage != null) {
            profileChangePasswordBinding.epNewPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

    public fun validateConPassword(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Confirm Password is required"

            return false
        } else if (value.length < 6) {
            errorMessage = "Confirm Password must be six characters long"

            return false
        }

        if (errorMessage != null) {
            profileChangePasswordBinding.epNewPasswordConTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

    public fun validatePasswordAndConPassword(valPara1: String, valPara2: String): Boolean {
        var errorMessage: String? = null
        val regPassword: String = valPara1
        val regConPassword: String = valPara2

        if (regPassword != regConPassword) {
            errorMessage = "Confirm Password doesn't match with the Password"

            return false
        }

        if (errorMessage != null) {
            profileChangePasswordBinding.epNewPasswordConTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return true
    }
*/


        public fun validateCurrentPassword(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Current Password is Required"
                profileChangePasswordBinding.epCurrentPasswordTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
                return false
            }

            return true
        }

        public fun validateNewPassword(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "New Password is Required"
                profileChangePasswordBinding.epNewPasswordTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
                return false
            } else if (value.length < 6) {
                errorMessage = "New Password must be six characters long"
                profileChangePasswordBinding.epNewPasswordTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
                return false
            }


            return true
        }

        public fun validateConPassword(valPara: String): Boolean {
            var errorMessage: String? = null
            val value: String = valPara
            if (value.isEmpty()) {
                errorMessage = "Confirm Password is required"
                profileChangePasswordBinding.epNewPasswordConTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
                return false
            } else if (value.length < 6) {
                errorMessage = "Confirm Password must be six characters long"
                profileChangePasswordBinding.epNewPasswordConTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
                return false
            }

            return true
        }

        public fun validatePasswordAndConPassword(valPara1: String, valPara2: String): Boolean {
            var errorMessage: String? = null
            val regPassword: String = valPara1
            val regConPassword: String = valPara2

            if (regPassword != regConPassword) {
                errorMessage = "Confirm Password doesn't match with the Password"
                profileChangePasswordBinding.epNewPasswordConTil.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
                return false
            }
            return true
        }


}