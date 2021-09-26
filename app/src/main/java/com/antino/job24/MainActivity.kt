package com.antino.job24

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.antino.job24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        initView()

    }
    private fun initView(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

    }

    override fun onBackPressed() {
        val des= navController.currentDestination?.id ?:-1
        when (des) {
            R.id.nav_rdashboard -> {

            }
            R.id.nav_cdashboard -> {

            }
            R.id.nav_login -> {

            }
            else -> {
                super.onBackPressed()
            }
        }
    }

}