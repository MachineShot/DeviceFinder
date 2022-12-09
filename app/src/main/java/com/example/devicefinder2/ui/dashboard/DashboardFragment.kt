package com.example.devicefinder2.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devicefinder2.R
import com.example.devicefinder2.databinding.FragmentDashboardBinding
import com.example.devicefinder2.ui.adapter.CalculationViewAdapter
import com.example.devicefinder2.ui.adapter.StrengthViewAdapter
import com.example.devicefinder2.ui.adapter.UserViewAdapter
import com.example.devicefinder2.ui.home.HomeViewModel
import com.example.devicefinder2.util.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*

@AndroidEntryPoint
class DashboardFragment : Fragment(), UserViewAdapter.UserItemListener {

    private var _binding: FragmentDashboardBinding? = null
    private var userAdapter: UserViewAdapter? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerview = binding.rvDashboard
        recyclerview.layoutManager = LinearLayoutManager(activity)

        userAdapter = UserViewAdapter(this)

        observeUserListUI(dashboardViewModel)

        recyclerview.adapter = userAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeUserListUI(viewModel: DashboardViewModel) {
        viewModel.userList.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { list ->
                        userAdapter?.submitList(list)
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
        Snackbar.make(dashboardFragment, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    override fun onClickedUser(userId: Int) {
        findNavController().navigate(
            R.id.userFragment_to_userDetailFragment, bundleOf("id" to userId)
        )
    }
}