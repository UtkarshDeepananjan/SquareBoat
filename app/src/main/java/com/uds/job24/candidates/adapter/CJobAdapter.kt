package com.uds.job24.candidates.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uds.job24.R
import com.uds.job24.candidates.CDashboardFragment
import com.uds.job24.candidates.model.CJobList
import com.uds.job24.databinding.ItemcjobBinding
import java.text.SimpleDateFormat
import java.util.*

class CJobAdapter(val result:List<CJobList.Datum>,val callback: CDashboardFragment) :
    RecyclerView.Adapter<CJobAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View
    private var qnt = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding: ItemcjobBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.itemcjob, parent, false
        )
        listItem = itemBinding.root
        context = itemBinding.root.context
        return ViewHolder(itemBinding)
    }


    override fun getItemCount(): Int {
        return if (result != null) {
            result.size
        } else {
            0
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            var ddate: String=""

            txtTitle.text=result[position].title ?:""
            txtDes.text=result[position].description ?:""
            txtLoc.text=result[position].location ?:""
            if (result[position].updatedAt!=null||result[position].updatedAt!="") {

                val dateFormat = SimpleDateFormat("yyyy-MM-dd")

                val format = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
                )
                val date = format.parse(result[position].updatedAt ?: "")

                val cal: Calendar = Calendar.getInstance()
                cal.time = date


                ddate = dateFormat.format(cal.time)
                txtDate.text = ddate
            }
            caradcont.setOnClickListener {
                callback.click(result[position].id ?:"",result[position].title ?:"",result[position].description ?:"",ddate,result[position].location ?:"")
            }

        }
    }


    class ViewHolder(itemBinding: ItemcjobBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding: ItemcjobBinding = itemBinding

    }
}