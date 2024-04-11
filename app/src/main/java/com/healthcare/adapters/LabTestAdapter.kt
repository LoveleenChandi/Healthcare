package com.healthcare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthcare.R
import com.healthcare.models.LabTestPackage

class LabTestAdapter(var labTestList: List<LabTestPackage>) :
    RecyclerView.Adapter<LabTestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.labtest_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentLabTest = labTestList[position]
        holder.labTestNameTextView.text = currentLabTest.name
        holder.labTestPriceTextView.text = "Price: ${currentLabTest.price}"
    }

    override fun getItemCount(): Int {
        return labTestList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labTestNameTextView: TextView = itemView.findViewById(R.id.labTestNameTextView)
        val labTestPriceTextView: TextView = itemView.findViewById(R.id.labTestPriceTextView)
    }
}
