package com.example.devicefinder2.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.databinding.StrengthDetailsBinding
import com.example.devicefinder2.databinding.UserDetailsBinding
import com.example.devicefinder2.ui.dashboard.DashboardViewModel
import com.example.devicefinder2.ui.dashboard.UserDetailsViewModel
import com.example.devicefinder2.util.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.user_details.*

@AndroidEntryPoint
class StrengthDetailsFragment : Fragment(){
    private var _binding: StrengthDetailsBinding? = null
    private val strengthDetailsViewModel by viewModels<StrengthDetailsViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = StrengthDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { strengthDetailsViewModel.getStrengthDetail(it) }
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        strengthDetailsViewModel.strength.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { strength ->
                        bindStrength(strength)
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

    private fun bindStrength(strength: Strength) {
        binding.id.text = "ID: " + strength.id.toString()
        binding.sensor.text = "Sensor: " + strength.sensor
        binding.calculation.text = "Calculation: " + strength.calculation.toString()
        binding.strength.text = "Strength: " + strength.strength.toString()
    }

    private fun showError(msg: String) {
        Snackbar.make(userDetailsFragment, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}