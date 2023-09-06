package com.inno.tatarbyhack.ui.navigation_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.databinding.BottomFragmentBinding
import com.inno.tatarbyhack.utils.ViewModelFactory

class NavigationFragment : Fragment() {

    private lateinit var binding: BottomFragmentBinding

    val viewModel : NavigationViewModel by activityViewModels { ViewModelFactory(App.appModule.coursesRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = BottomFragmentBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.navView.setupWithNavController(navController)


    }
}