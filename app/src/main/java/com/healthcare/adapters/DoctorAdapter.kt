package com.healthcare.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthcare.BookAppointment
import com.healthcare.R
import com.healthcare.models.Doctor

class DoctorAdapter(var doctorList: List<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.doctor_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoctor = doctorList[position]
        holder.doctorNameTextView.text = currentDoctor.name
        holder.doctorSpecialtyTextView.text = currentDoctor.specialty
        holder.bookAppointmentButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, BookAppointment::class.java)
            intent.putExtra("DOCTOR_NAME", currentDoctor.name)
            intent.putExtra("DOCTOR_SPECIALTY", currentDoctor.specialty)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorNameTextView: TextView = itemView.findViewById(R.id.doctorNameTextView)
        val doctorSpecialtyTextView: TextView =
            itemView.findViewById(R.id.doctorSpecialtyTextView)
        val bookAppointmentButton: Button = itemView.findViewById(R.id.bookAppointmentButton)
    }
}
