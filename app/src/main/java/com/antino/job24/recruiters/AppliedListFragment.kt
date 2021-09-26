package com.antino.job24.recruiters

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.job24.common.utils.Loadinddialog
import com.antino.job24.databinding.FragmentAppliedListBinding
import com.antino.job24.recruiters.adapter.CandidateAdapter
import com.antino.job24.recruiters.viewmodel.RDashboardViewModel
import kotlinx.coroutines.launch


class AppliedListFragment : Fragment() {


    private lateinit var binding: FragmentAppliedListBinding
    private lateinit var dialog: Loadinddialog
    private lateinit var viewModel: RDashboardViewModel

    private lateinit var id: String
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var location: String
    private lateinit var ddate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppliedListBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {

        dialog = Loadinddialog()
        viewModel = ViewModelProvider(this).get(RDashboardViewModel::class.java)

        binding.apply {

            val bundle: Bundle? = getArguments()
            if (bundle != null) {
                id = bundle.getString("id", "")
                title = bundle.getString("title", "")
                description = bundle.getString("description", "")
                location = bundle.getString("location", "")
                ddate = bundle.getString("ddate", "")
            }

            txtTitle.text = title
            txtDes.text = description
            txtDate.text = ddate
            txtLoc.text = location

            btnBack.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .popBackStack()
            }
            recAppliedList.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext())
                itemAnimator = DefaultItemAnimator()
                isNestedScrollingEnabled = false
            }
            getCandidate()

        }
    }
    private fun getCandidate(){

        lifecycleScope.launch {
            if (!dialog.isShowing())
                dialog.create(requireContext())
            viewModel.getCandidateJob(requireContext(), id)
            viewModel.responseCandidateList.observe(viewLifecycleOwner,
                Observer {

                    if (dialog.isShowing())
                        dialog.dismiss()
                    if (it?.code ?: 0 == 200) {
                        if (dialog.isShowing())
                            dialog.dismiss()
                        if (it?.data!=null) {
                            binding.recAppliedList.adapter = CandidateAdapter(it.data)

                            if(it.data.size==0)
                                binding.txtNoapplicant.visibility=View.VISIBLE
                            else
                                binding.txtNoapplicant.visibility=View.GONE

                        }else
                            binding.txtNoapplicant.visibility=View.VISIBLE
                    } else {
                        Toast.makeText(requireContext(), "${it?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
        }
    }
}