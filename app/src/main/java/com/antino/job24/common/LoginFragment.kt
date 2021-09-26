package com.antino.job24.common

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.antino.job24.R
import com.antino.job24.common.utils.Loadinddialog
import com.antino.job24.common.utils.UserPreferences
import com.antino.job24.common.viewModel.CommonViewModel
import com.antino.job24.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding


    private lateinit var viewModel: CommonViewModel
    private var userPreferences: UserPreferences? = null

    private lateinit var dialog: Loadinddialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }
    private fun init(){
        dialog = Loadinddialog()
        userPreferences = UserPreferences(requireContext())
        viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)

        binding.apply {
            txtSignup.setOnClickListener {
                Navigation.findNavController(loginBtn)
                    .navigate(R.id.nav_Register)
            }
            loginBtn.setOnClickListener {
                validate()
            }
        }
    }


    private fun validate() {
        binding.apply {
            if (
                Validation.emptyField(
                    data = edtPass,
                    message = "Enter Password",
                    error = edtPassLayout
                ) &&
                Validation.emailField(
                    data = edtEmail,
                    error = edtEmailLayout,
                    emptymessage = "Enter Email",
                    message = "Enter Email"
                )&& Validation.FieldWithLength(
                    data = edtPass,
                    error = edtPassLayout,
                    message = "Enter Minimum of 6",6
                )
            ) {

                lifecycleScope.launch {
                    if (!dialog.isShowing())
                        dialog.create(requireContext())
                    viewModel.login(
                        email = edtEmail.text.toString(),
                        password = edtPass.text.toString()
                    )
                    viewModel.responseLogin.observe(viewLifecycleOwner,
                        Observer {

                            if (dialog.isShowing())
                                dialog.dismiss()
                            if (it?.code ?: 0 == 200) {
                                lifecycleScope.launch {
                                    if (it?.data!=null) {
                                        userPreferences!!.saveAuthtoke(it.data.token ?: "")
                                        userPreferences!!.saveid(it.data.id ?: "")
                                        userPreferences!!.saverole(it.data.userRole ?: -1)

                                        if (it.data.userRole ?: -1 == 0)
                                            Navigation.findNavController(loginBtn)
                                                .navigate(R.id.action_nav_login_to_nav_rdashboard)
                                        else if (it.data.userRole ?: -1 == 1)
                                            Navigation.findNavController(loginBtn)
                                                .navigate(R.id.action_nav_login_to_nav_cdashboard)
                                    }
                                }
                            }else {
                                Toast.makeText(requireContext(),"${it?.message}", Toast.LENGTH_SHORT).show()
                            }

                        })
                }
            }else{
                Log.d("TAG", "validate: else")
            }
        }
    }
}