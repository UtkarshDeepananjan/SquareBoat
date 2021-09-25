package com.uds.job24.common

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.uds.job24.R
import com.uds.job24.common.utils.UserPreferences
import com.uds.job24.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private var userPreferences: UserPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init(){

        userPreferences = UserPreferences(requireContext())

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                val token = userPreferences?.authtoken?.buffer()?.first()  ?: ""
                val userrole = userPreferences?.userrole?.buffer()?.first()  ?: -1
                Log.d("TAG", "init: $token \n $userrole")
                if (token != "") {
                    if (userrole == 0)
                        Navigation.findNavController(binding.imgTop)
                            .navigate(R.id.nav_rdashboard)
                    else if (userrole == 1)
                        Navigation.findNavController(binding.imgTop)
                            .navigate(R.id.nav_cdashboard)
                    else Navigation.findNavController(binding.imgTop)
                        .navigate(R.id.nav_login)
                }else Navigation.findNavController(binding.imgTop)
                .navigate(R.id.nav_login)

            }
            @Suppress("DEPRECATION")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

                requireActivity().window.insetsController?.show(WindowInsets.Type.statusBars())
            } else {
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }, 3000)
    }

}