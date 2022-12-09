package com.example.devicefinder2.ui.calculation

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import androidx.lifecycle.Observer
import com.example.devicefinder2.R
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.data.model.UserCalc
import com.example.devicefinder2.databinding.CalculationCardBinding
import com.example.devicefinder2.databinding.FragmentHomeBinding
import com.example.devicefinder2.databinding.UserInsertBinding
import com.example.devicefinder2.ui.adapter.GridViewCustomAdapter
import com.example.devicefinder2.ui.home.HomeFragment
import com.example.devicefinder2.ui.home.HomeViewModel
import com.example.devicefinder2.util.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.user_card.*
import kotlinx.android.synthetic.main.user_insert.*
import kotlinx.android.synthetic.main.user_insert.view.*

@AndroidEntryPoint
class CalculationFragment : Fragment() {

    private var _binding: UserInsertBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calculationViewModel =
            ViewModelProvider(this).get(CalculationViewModel::class.java)

        _binding = UserInsertBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.btnInsert.setOnClickListener {
            val user = UserCalc(inputMac.text.toString(), ArrayList(arrayListOf(inputR1.text.toString().toInt(), inputR2.text.toString().toInt(), inputR3.text.toString().toInt())))
            tvResult.text = calculationViewModel.findUser(user)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}