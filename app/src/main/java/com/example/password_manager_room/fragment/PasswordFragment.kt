package com.example.password_manager_room.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.password_manager_room.databinding.FragmentPasswordBinding

class PasswordFragment : Fragment() {

    private lateinit var binding : FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerate.setOnClickListener {
            generatePassword(12)
        }

        binding.imgCopy.setOnClickListener {
            copyPassword()
        }
    }

    private fun copyPassword() {
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("", binding.txtPassword.text.toString())
        clipboard.setPrimaryClip(clipData)
        Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun generatePassword(length: Int) {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789!@#$%^&*(){}?;"

        val sb = StringBuilder(length)

        for (x in 0 until length){
            val randomPassword = (characters.indices).random()
            sb.append(characters[randomPassword])
        }
        sb.insert((0 until length).random(), "")
        binding.txtPassword.text = sb
        binding.imgCopy.visibility = View.VISIBLE
    }
}