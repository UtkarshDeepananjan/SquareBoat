package com.antino.job24.recruiters.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.antino.job24.R
import com.antino.job24.databinding.RecruiterJobPostsListBinding
import com.antino.job24.recruiters.RecruiterDashboardFragment
import com.antino.job24.recruiters.callback.JobListItemClickListener
import com.antino.job24.recruiters.model.RJobList
import java.text.SimpleDateFormat
import java.util.*

class RecruiterJobPostAdapter(
    private var result: List<RJobList.Data.Datum>,
    private val callback: JobListItemClickListener
) :
    RecyclerView.Adapter<RecruiterJobPostAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View
    private var qnt = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding: RecruiterJobPostsListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recruiter_job_posts_list, parent, false
        )
        listItem = itemBinding.root
        context = itemBinding.root.context
        return ViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return result.size
    }

    fun setAdapter(result: List<RJobList.Data.Datum>) {
        this.result = result
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            var mDate = ""

            tvJobTitle.text = result[position].title ?: ""
            tvJobDescription.text = result[position].description ?: ""
            tvLocation.text = result[position].location ?: ""
            if (result[position].updatedAt != null || result[position].updatedAt != "") {

                val dateFormat = SimpleDateFormat("yyyy-MM-dd")

                val format = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
                )
                val date = format.parse(result[position].updatedAt ?: "")

                val cal: Calendar = Calendar.getInstance()
                cal.time = date


                mDate = dateFormat.format(cal.time)
                tvJobPostDate.text = mDate
            }
            ivDelete.setOnClickListener {
                callback.deleteJob(result[position].id ?: "")
            }
            cardView.setOnClickListener {
                callback.detailJob(
                    result[position].id ?: "",
                    result[position].title ?: "",
                    result[position].description ?: "",
                    result[position].location ?: "",
                    mDate
                )
            }

        }
    }


    class ViewHolder(itemBinding: RecruiterJobPostsListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding: RecruiterJobPostsListBinding = itemBinding

    }
}