package com.antino.job24.recruiters.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.antino.job24.R
import com.antino.job24.databinding.ItemcandidateBinding
import com.antino.job24.recruiters.model.CandidateList

class CandidateAdapter(val result:List<CandidateList.Datum>) :
    RecyclerView.Adapter<CandidateAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var listItem: View
    private var qnt = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding: ItemcandidateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.itemcandidate, parent, false
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
            txtTitle.text=result[position].name ?:""
            txtEmail.text=result[position].email ?:""
            txtSkill.text=result[position].skills ?:""


        }
    }


    class ViewHolder(itemBinding: ItemcandidateBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val binding: ItemcandidateBinding = itemBinding

    }
}