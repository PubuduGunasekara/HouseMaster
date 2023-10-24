package com.example.housemaster

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentResetPasswordBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
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
            if (email.isNotEmpty()) {


                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val action =
                                ResetPasswordFragmentDirections.actionResetPasswordFragmentToRPDoneFragment()
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error sending the link, Please try again later",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Empty fields are not allowed", Toast.LENGTH_LONG).show()
            }

        }


    }
}