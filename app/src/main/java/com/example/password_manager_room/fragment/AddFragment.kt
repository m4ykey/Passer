package com.example.password_manager_room.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.password_manager_room.MainActivity
import com.example.password_manager_room.R
import com.example.password_manager_room.data.Account
import com.example.password_manager_room.databinding.FragmentAddBinding
import com.example.password_manager_room.mvvm.AccountViewModel

class AddFragment : Fragment() {

    private lateinit var binding : FragmentAddBinding
    private lateinit var viewModel : AccountViewModel
    val args by navArgs<AddFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }

        args.account?.let {
            binding.apply {
                etCompany.setText(it.company)
                etEmail.setText(it.email)
                etPassword.setText(it.password)
            }
            binding.btnAdd.text = "Update"
            binding.imgDelete.visibility = View.VISIBLE
        }

        binding.apply {
            binding.btnAdd.setOnClickListener {
                val id = args.account?.id ?: 0
                val email = etEmail.text.toString()
                val company = etCompany.text.toString()
                val password = etPassword.text.toString()

                Account(id, email, company, password).also{ account ->
                    if (email.isEmpty() || company.isEmpty() || password.isEmpty()){
                        Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    viewModel.upsertAccount(account)
                    findNavController().navigateUp()
                }
            }
        }

        binding.apply {
            binding.imgDelete.setOnClickListener{
                val accountId = args.account!!.id
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val company = etCompany.text.toString()

                Account(accountId, email, company, password).also{
                    viewModel.deleteAccount(it)
                    findNavController().navigateUp()
                }
            }
        }
    }
}