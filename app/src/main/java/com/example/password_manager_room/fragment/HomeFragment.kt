package com.example.password_manager_room.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.password_manager_room.MainActivity
import com.example.password_manager_room.R
import com.example.password_manager_room.adapter.AccountAdapter
import com.example.password_manager_room.databinding.FragmentHomeBinding
import com.example.password_manager_room.mvvm.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : AccountViewModel
    private lateinit var accountAdapter : AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.accounts.collect{ accountList ->
                accountAdapter.differ.submitList(accountList)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchAccount.collect{ searchList ->
                accountAdapter.differ.submitList(searchList)
            }
        }

        binding.etSearch.addTextChangedListener {
            viewModel.searchInAccount(it.toString().trim())
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        accountAdapter.onClick = { account ->
            val bundle = Bundle().apply {
                putParcelable("account", account)
            }
            findNavController().navigate(R.id.action_homeFragment_to_addFragment, bundle)
        }
    }

    private fun setupRecyclerView() {
        accountAdapter = AccountAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountAdapter
        }
    }
}