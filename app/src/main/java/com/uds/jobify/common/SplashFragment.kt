package com.uds.jobify.common

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.antino.job24.R
import com.antino.job24.databinding.FragmentSplashBinding
import com.uds.jobify.common.utils.UserPreferences
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private var userPreferences: UserPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {

        userPreferences = UserPreferences(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                val token = userPreferences?.authtoken?.buffer()?.first() ?: ""
                val userRole = userPreferences?.userrole?.buffer()?.first() ?: -1
                val navController =
                    activity?.let { Navigation.findNavController(it, R.id.nav_host_fragment) }
                if (token != "") {
                    when (userRole) {
                        0 -> navController?.navigate(R.id.nav_rdashboard)
                        1 -> navController?.navigate(R.id.nav_cdashboard)
                        else -> navController?.navigate(R.id.nav_login)
                    }
                } else {
                    activity?.let {
                        navController?.navigate(R.id.nav_login)
                    }
                }

            }
        }, 3000)
    }

}