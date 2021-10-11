package com.skl.foodrecipeapp.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skl.foodrecipeapp.R
import com.skl.foodrecipeapp.databinding.FragmentSettingsBinding
import com.skl.foodrecipeapp.presentation.BaseFragment


class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override fun getFragmentBinding(): FragmentSettingsBinding = FragmentSettingsBinding.inflate(layoutInflater)
}