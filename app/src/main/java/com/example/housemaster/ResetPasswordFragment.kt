package com.example.housemaster

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentResetPasswordBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth


class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {

    private lateinit var resetPasswordBinding: FragmentResetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetPasswordBinding = FragmentResetPasswordBinding.bind(view)

        firebaseAuth = FirebaseAuth.getInstance()

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE


        resetPasswordBinding.btnSendEmail.setOnClickListener {
            val email = resetPasswordBinding.forgotPwEmail.text.toString()
            if (validateEmail(resetPasswordBinding.forgotPwEmail.text.toString())) {


                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                                .setMessage("Reset link has been sent to the email entered successfully.")
                                .setCancelable(false)
                                .setPositiveButton("Done") { dialog_, which ->

                                    val action =
                                        ResetPasswordFragmentDirections.actionResetPasswordFragmentToSignInFragment()
                                    findNavController().navigate(action)


                                }.show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                task.exception?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                validateEmail(resetPasswordBinding.forgotPwEmail.text.toString())
            }

        }


    }


   /* private fun validateEmail(valPara:String): Boolean {
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
            resetPasswordBinding.forgotEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
                return false
            }
        }
        return true
    }*/
    public fun validateEmail(valPara:String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Email is required"
            resetPasswordBinding.forgotEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"
            resetPasswordBinding.forgotEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }
}