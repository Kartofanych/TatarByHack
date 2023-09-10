package com.inno.tatarbyhack.ui.navigation_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.databinding.BottomFragmentBinding
import com.inno.tatarbyhack.domain.models.Role
import com.inno.tatarbyhack.utils.SharedPreferencesHelper
import com.inno.tatarbyhack.utils.ViewModelFactory

class NavigationFragment : Fragment() {

    private lateinit var binding: BottomFragmentBinding
    private val sharedPreferencesHelper = SharedPreferencesHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = BottomFragmentBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_fragment_container) as NavHostFragment

        val user = sharedPreferencesHelper.getUser()

        if (sharedPreferencesHelper.a == 0) {
            val inflater = navHostFragment.navController.navInflater
            val graph =
                if (user.role == Role.Student) inflater.inflate(R.navigation.main_user_navigation)
                else inflater.inflate(R.navigation.main_navigation)

            navHostFragment.navController.graph = graph

            sharedPreferencesHelper.a = 1
        }

        if (user.role == Role.Student) {
            binding.navView.menu.clear()
            binding.navView.inflateMenu(R.menu.bottom_nav_user_menu)
        }


        val navController = navHostFragment.findNavController()
        binding.navView.setupWithNavController(navController)

    }
}