package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentLoginBinding
import com.example.housemaster.databinding.FragmentReviewBeforeConfirmAppBinding
import com.example.housemaster.databinding.FragmentWelcomeBinding

class ReviewBeforeConAppFragment : Fragment(R.layout.fragment_review_before_confirm_app) {
    private lateinit var reviewBeforeConfirmAppBinding: FragmentReviewBeforeConfirmAppBinding
    private val args: ReviewBeforeConAppFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reviewBeforeConfirmAppBinding = FragmentReviewBeforeConfirmAppBinding.bind(view)

        reviewBeforeConfirmAppBinding.temp.text =
            "" + args.isHomeService + "" + args.appDate + "" + args.appServiceType + "" + args.appTimeSlot

        reviewBeforeConfirmAppBinding.buttonOkay.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
            findNavController().navigate(action)
        }

    }
}