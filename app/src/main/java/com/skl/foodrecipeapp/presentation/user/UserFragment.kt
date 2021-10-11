package com.skl.foodrecipeapp.presentation.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skl.foodrecipeapp.R
import com.skl.foodrecipeapp.databinding.FragmentUserBinding
import com.skl.foodrecipeapp.presentation.BaseFragment

class UserFragment : BaseFragment<FragmentUserBinding>() {

    override fun getFragmentBinding(): FragmentUserBinding = FragmentUserBinding.inflate(layoutInflater)
}