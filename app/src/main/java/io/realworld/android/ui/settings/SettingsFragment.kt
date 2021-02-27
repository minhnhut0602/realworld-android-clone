package io.realworld.android.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.realworld.android.AuthViewModel
import io.realworld.android.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val authViewModel by activityViewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.user.observe({lifecycle}){
            _binding?.apply {
                usernameEditText.setText(it?.username)
                emailEditText.setText(it?.email)
                bioEditText.setText(it?.bio)
            }
        }

        _binding?.apply {
            submitButton.setOnClickListener {
                authViewModel.updateUser(
                    username = usernameEditText.text.toString().takeIf{it.isNotBlank()},
                    email = emailEditText.text.toString().takeIf{it.isNotBlank()},
                    password = passwordEditText.text.toString().takeIf{it.isNotBlank()},
                    bio = bioEditText.text.toString(),
                    image = imageEditText.text.toString()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}