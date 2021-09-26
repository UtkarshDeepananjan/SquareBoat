package com.antino.job24.recruiters.callback

interface JobListItemClickListener {
    fun deleteJob(id:String)
    fun detailJob(id:String,title:String,description:String,location:String,ddate:String)
}