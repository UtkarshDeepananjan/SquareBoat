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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.job24.R
import com.antino.job24.common.utils.Loadinddialog
import com.antino.job24.databinding.FragmentRecruiterDashboardBinding
import com.antino.job24.databinding.RecruiterJobPostsListBinding
import com.antino.job24.recruiters.adapter.RecruiterJobPostAdapter
import com.antino.job24.recruiters.callback.JobListItemClickListener
import com.antino.job24.recruiters.viewmodel.RDashboardViewModel
import kotlinx.coroutines.launch


class RecruiterDashboardFragment : Fragment(), JobListItemClickListener {

    private lateinit var binding: FragmentRecruiterDashboardBinding

    private lateinit var dialog: Loadinddialog
    private lateinit var viewModel: RDashboardViewModel
    var adapter=RecruiterJobPostAdapter(arrayListOf(),this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecruiterDashboardBinding.inflate(inflater,container,false)
        init()
        return binding.root
    }

    private fun init() {
        dialog = Loadinddialog()
        viewModel = ViewModelProvider(this).get(RDashboardViewModel::class.java)

        binding.apply {
            postJob.setOnClickListener {

                Navigation.findNavController(binding.postJob)
                    .navigate(R.id.action_nav_rdashboard_to_nav_createjob)
            }
            rvPostedJob.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext())
                itemAnimator = DefaultItemAnimator()
            }
            rvPostedJob.adapter=adapter
        }

        getPostedJob()

    }

    private fun getPostedJob() {

        lifecycleScope.launch {
            if (!dialog.isShowing())
                dialog.create(requireContext())
            viewModel.getcreatedJob(requireContext())
            viewModel.responseRJobList.observe(viewLifecycleOwner,
                Observer {

                    if (dialog.isShowing())
                        dialog.dismiss()
                    if (it?.code ?: 0 == 200) {
                        if (dialog.isShowing())
                            dialog.dismiss()
                        if (it?.data != null) {
                            adapter.setAdapter(it.data.data!!)
                        }


                    } else {
                        Toast.makeText(requireContext(), "${it?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
        }
    }

    override fun deleteJob(id: String) {
        lifecycleScope.launch {
            if (!dialog.isShowing())
                dialog.show()
            viewModel.deleteJob(requireContext(), jobid = id)
            viewModel.responsedeleteResponseBody.observe(viewLifecycleOwner,
                Observer {

                    if (dialog.isShowing())
                        dialog.dismiss()
                    if (it == null)
                        getPostedJob()

                })
        }
    }

    override fun detailJob(
        id: String,
        title: String,
        description: String,
        location: String,
        ddate: String
    ) {
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("title", title)
        bundle.putString("description", description)
        bundle.putString("location", location)
        bundle.putString("ddate", ddate)
        Navigation.findNavController(binding.rvPostedJob)
            .navigate(R.id.action_nav_rdashboard_to_nav_appliedlist, bundle)
    }
}