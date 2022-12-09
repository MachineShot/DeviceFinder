package com.example.devicefinder2.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.devicefinder2.DeviceFinderApplication
import com.example.devicefinder2.R
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.databinding.UserDetailsBinding
import com.example.devicefinder2.util.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.user_card.*
import kotlinx.android.synthetic.main.user_details.*

@AndroidEntryPoint
class UserDetailsFragment : Fragment(){
    private var _binding: UserDetailsBinding? = null
    private val userDetailsViewModel by viewModels<UserDetailsViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = UserDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupObservers()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { userDetailsViewModel.getUserDetail(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        userDetailsViewModel.user.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { user ->
                        bindUser(user)
                    }
                    progress.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        showError(it)
                    }
                    progress.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun bindUser(user: User) {
        binding.textView.text = user.mac
    }

    private fun showError(msg: String) {
        Snackbar.make(userDetailsFragment, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}