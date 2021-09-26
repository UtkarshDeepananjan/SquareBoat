package com.antino.job24.recruiters

import android.os.Bundle
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
import com.antino.job24.common.Validation
import com.antino.job24.common.utils.Loadinddialog
import com.antino.job24.databinding.FragmentCreateJobBinding
import com.antino.job24.recruiters.viewmodel.RDashboardViewModel
import kotlinx.coroutines.launch


class CreateJobFragment : Fragment() {

    private lateinit var binding: FragmentCreateJobBinding
    private lateinit var dialog: Loadinddialog
    private lateinit var viewModel: RDashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateJobBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {

        dialog = Loadinddialog()
        viewModel = ViewModelProvider(this).get(RDashboardViewModel::class.java)

        binding.apply {
            btnBack.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .popBackStack()
            }
            binding.btnSubmit.setOnClickListener {
                if (
                    Validation.emptyField(
                        data = edtTitle,
                        message = "Enter Title",
                        error = edtTitleLayout
                    ) &&
                    Validation.emptyField(
                        data = edtDescription,
                        message = "Enter Description",
                        error = edtDescriptionLayout
                    ) &&
                    Validation.emptyField(
                        data = edtLocation,
                        error = edtLocationLayout,
                        message = "Enter Location"
                    )
                ) {


                    lifecycleScope.launch {
                        if (!dialog.isShowing())
                            dialog.create(requireContext())
                        viewModel.createJob(
                            context = requireContext(),
                            title = edtTitle.text.toString(),
                            des = edtDescription.text.toString(),
                            loc = edtLocation.text.toString()
                        )
                        viewModel.responseCreateJob.observe(viewLifecycleOwner,
                            Observer {

                                if (dialog.isShowing())
                                    dialog.dismiss()
                                if (it?.code ?: 0 == 201) {
                                    lifecycleScope.launch {
                                        Navigation.findNavController(binding.root)
                                            .popBackStack()
                                    }
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "${it?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            })
                    }
                }
            }
        }
    }

}