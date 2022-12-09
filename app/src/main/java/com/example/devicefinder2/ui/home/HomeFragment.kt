package com.example.devicefinder2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.devicefinder2.ui.adapter.GridViewCustomAdapter
import com.example.devicefinder2.databinding.FragmentHomeBinding
import com.example.devicefinder2.util.Result
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapter : GridViewCustomAdapter? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val list: GridView = binding.gridView
        observeStrengthListUI(homeViewModel)
        observeUserListUI(homeViewModel)
        observeCalculationListUI(homeViewModel)
        adapter = GridViewCustomAdapter(layoutInflater)
        list.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeCalculationListUI(viewModel: HomeViewModel) {
        viewModel.calculationList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { list ->
                        adapter?.submitCalculationList(list)
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

    private fun observeStrengthListUI(viewModel: HomeViewModel) {
        viewModel.strengthList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { list ->
                        adapter?.submitStrengthList(list)
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

    private fun observeUserListUI(viewModel: HomeViewModel) {
        viewModel.userList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let { list ->
                        adapter?.submitUserList(list)
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

    private fun showError(msg: String) {
        Snackbar.make(homeFragment, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}