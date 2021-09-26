package com.antino.job24.candidates

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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.job24.R
import com.antino.job24.candidates.adapter.CJobAdapter
import com.antino.job24.candidates.callback.CCallback
import com.antino.job24.candidates.model.CJobList
import com.antino.job24.candidates.viewmodel.CDashboardViewModel
import com.antino.job24.common.utils.Loadinddialog
import com.antino.job24.common.utils.UserPreferences
import com.antino.job24.common.viewModel.CommonViewModel
import com.antino.job24.databinding.FragmentCDashboardBinding
import com.antino.job24.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CDashboardFragment : Fragment(), CCallback {

    private lateinit var binding: FragmentCDashboardBinding


    private lateinit var viewModel: CDashboardViewModel
    private var userPreferences: UserPreferences? = null

    private lateinit var dialog: Loadinddialog
    private lateinit var appliedList: ArrayList<CJobList.Datum>

    private var currentid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCDashboardBinding.inflate(inflater, container, false)

        init()
        return binding.root
    }

    private fun init() {
        dialog = Loadinddialog()
        appliedList = ArrayList()
        userPreferences = UserPreferences(requireContext())
        viewModel = ViewModelProvider(this).get(CDashboardViewModel::class.java)

        binding.apply {
            recListJob.apply {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext())
                itemAnimator = DefaultItemAnimator()
                isNestedScrollingEnabled = false
            }
            getJobList()
        }
        applyJob()
        getAppliedList()
    }

    private fun applyJob() {
        binding.btnApply.setOnClickListener {

            lifecycleScope.launch {
                if (!dialog.isShowing())
                    dialog.create(requireContext())
                viewModel.applyJob(requireContext(), currentid)
                viewModel.responseCApply.observe(viewLifecycleOwner,
                    Observer {

                        if (dialog.isShowing())
                            dialog.dismiss()
                        if (it?.code ?: 0 == 201) {
                            if (dialog.isShowing())
                                dialog.dismiss()
                            binding.consCart.visibility = View.GONE
                            binding.consCartApplied.visibility = View.VISIBLE
                            getAppliedList()
                            getJobList()
                        } else {
                            Toast.makeText(requireContext(), "${it?.message}", Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
            }

        }
    }

    private fun filter(){
        Log.d("TAG", "filter: ${appliedList.size}")
        binding.consCart.visibility = View.VISIBLE
        binding.consCartApplied.visibility = View.GONE
        for (i in 0 until appliedList.size){
            Log.d("TAG", "filter: applied list ${appliedList[i].id} current ${currentid}")
            if (appliedList[i].id==currentid) {
                binding.consCart.visibility = View.GONE
                binding.consCartApplied.visibility = View.VISIBLE
            }
        }
    }

    private fun getAppliedList() {
        lifecycleScope.launch {
            if (!dialog.isShowing())
                dialog.create(requireContext())
            viewModel.appliedJobList(requireContext())
            viewModel.responseCappliedJobList.observe(viewLifecycleOwner,
                Observer {

                    if (dialog.isShowing())
                        dialog.dismiss()
                    if (it?.code ?: 0 == 200) {
                        if (dialog.isShowing())
                            dialog.dismiss()

                        if (it?.data != null) {
                            appliedList.clear()
                            appliedList.addAll(it.data)
                        }
                        filter()
                    } else {
                        Toast.makeText(requireContext(), "${it?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
        }

    }

    private fun getJobList() {
        lifecycleScope.launch {
            if (!dialog.isShowing())
                dialog.create(requireContext())
            viewModel.getJob(requireContext())
            viewModel.responseCJobList.observe(viewLifecycleOwner,
                Observer {

                    if (dialog.isShowing())
                        dialog.dismiss()
                    if (it?.code ?: 0 == 200) {
                        lifecycleScope.launch {
                            if (it?.data != null) {
                                binding.apply {
                                    recListJob.adapter =
                                        CJobAdapter(it.data, this@CDashboardFragment)
                                    if (it.data.size > 0) {
                                        txtTitle.text = it.data[0].title
                                        txtDes.text = it.data[0].description
                                        txtLoc.text = it.data[0].location

                                        currentid = it.data[0].id.toString()

                                        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

                                        val format = SimpleDateFormat(
                                            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
                                        )
                                        val date = format.parse(it.data[0].updatedAt ?: "")

                                        val cal: Calendar = Calendar.getInstance()
                                        cal.time = date


                                        val ddate = dateFormat.format(cal.time)

                                        txtDate.text = ddate
                                        getAppliedList()
                                    } else {
                                        binding.consCart.visibility = View.GONE
                                        binding.consCartApplied.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    } else {
                        binding.consCart.visibility = View.GONE
                        binding.consCartApplied.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "${it?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
        }

    }

    override fun click(id: String, title: String, des: String, date: String, location: String) {
        binding.apply {
            txtTitle.text = title
            txtDes.text = des
            txtLoc.text = location
            currentid = id
            txtDate.text = date

            filter()
        }
    }


}