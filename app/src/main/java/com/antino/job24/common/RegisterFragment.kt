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
import com.antino.job24.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private lateinit var viewModel: CommonViewModel
    private var userPreferences: UserPreferences? = null

    private lateinit var dialog: Loadinddialog
    private var role = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    private fun init() {
        dialog = Loadinddialog()
        userPreferences = UserPreferences(requireContext())
        viewModel = ViewModelProvider(this).get(CommonViewModel::class.java)

        binding.apply {
            registerBtn.setOnClickListener {
                validate()
            }
            registerBtn.setOnClickListener {
                Navigation.findNavController(registerBtn)
                    .navigate(R.id.nav_login)
            }
            roleRg.setOnCheckedChangeListener { radioGroup, i ->
                when (i) {
                    R.id.job_seeker_rb -> role = 1
                    R.id.recruiter_rb -> role = 0
                }
            }
        }
    }

    private fun validate() {
        binding.apply {
            if (Validation.emptyField(
                    data = edtName,
                    message = "Enter Name",
                    error = edtNameLayout
                )  &&
                Validation.emptyField(
                    data = edtPass,
                    message = "Enter Password",
                    error = edtPassLayout
                ) && Validation.emptyField(
                    data = edtConPass,
                    message = "Enter Comf Password",
                    error = edtConPassLayout
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
                )&& Validation.FieldWithLength(
                    data = edtConPass,
                    error = edtConPassLayout,
                    message = "Enter Minimum of 6",6
                )
            ) {

                lifecycleScope.launch {
                    if (!dialog.isShowing())
                        dialog.create(requireContext())
                    viewModel.register(
                        name = edtName.text.toString(),
                        email = edtEmail.text.toString(),
                        password = edtPass.text.toString(),
                        confirmPassword = edtConPass.text.toString(),
                        skills = "HTML, CSS, JS",
                        userRole = 0
                    )
                    viewModel.responseRegster.observe(viewLifecycleOwner,
                        Observer {

                            if (dialog.isShowing())
                                dialog.dismiss()
                            if (it?.code ?: 0 == 201) {
                                lifecycleScope.launch {
                                    userPreferences!!.saveAuthtoke(it?.data?.token ?: "")
                                    userPreferences!!.saveid(it?.data?.id ?: "")
                                    userPreferences!!.saverole(it?.data?.userRole ?: -1)
                                    Navigation.findNavController(registerBtn)
                                        .navigate(R.id.nav_login)
                                }
                            }else if (it?.code ?: 0 == 422){
                                Toast.makeText(requireContext(),"${it?.message}",Toast.LENGTH_SHORT).show()
                            }

                        })
                }
            }else{
                Log.d("TAG", "validate: else")
            }
        }
    }

}