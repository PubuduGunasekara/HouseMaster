package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentLoginBinding
import com.example.housemaster.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    private lateinit var welcomeBinding: FragmentWelcomeBinding
    private val args: WelcomeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        welcomeBinding = FragmentWelcomeBinding.bind(view)

        welcomeBinding.textViewUsername.text = args.username
        welcomeBinding.textViewPassword.text = args.password

        welcomeBinding.buttonOkay.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
            findNavController().navigate(action)
        }

    }
}