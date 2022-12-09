package com.example.devicefinder2.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devicefinder2.R
import com.example.devicefinder2.databinding.FragmentNotificationsBinding
import com.example.devicefinder2.ui.adapter.StrengthViewAdapter
import com.example.devicefinder2.ui.adapter.UserViewAdapter
import com.example.devicefinder2.ui.dashboard.DashboardViewModel
import com.example.devicefinder2.util.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.loading
import kotlinx.android.synthetic.main.fragment_notifications.*

@AndroidEntryPoint
class NotificationsFragment : Fragment(), StrengthViewAdapter.StrengthItemListener {

    private var _binding: FragmentNotificationsBinding? = null
    private var strengthAdapter: StrengthViewAdapter? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerview = binding.rvNotification
        recyclerview.layoutManager = LinearLayoutManager(activity)

        strengthAdapter = StrengthViewAdapter(this)

        observeStrengthListUI(notificationsViewModel)

        recyclerview.adapter = strengthAdapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeStrengthListUI(viewModel: NotificationsViewModel) {
        viewModel.strengthList.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { list ->
                        strengthAdapter?.submitList(list)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        showError(it)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showError(msg: String) {
        Snackbar.make(notificationsFragment, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    override fun onClickedStrength(strengthId: Int) {
        findNavController().navigate(
            R.id.strengthFragment_to_strengthDetailFragment, bundleOf("id" to strengthId)
        )
    }
}