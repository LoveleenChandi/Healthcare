package com.healthcare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthcare.R
import com.healthcare.models.Appointment

class AppointmentsAdapter(var appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTimeTextView: TextView = itemView.findViewById(R.id.textViewDateTime)
        val doctorNameTextView: TextView = itemView.findViewById(R.id.textViewDoctorName)
        val specialtyTextView: TextView = itemView.findViewById(R.id.textViewSpecialty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.dateTimeTextView.text =
            appointment.appointmentTime + " " + appointment.appointmentTime
        holder.doctorNameTextView.text = appointment.doctorName
        holder.specialtyTextView.text = appointment.specialty
    }

    override fun getItemCount(): Int {
        return appointments.size
    }
}
