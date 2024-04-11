package com.healthcare

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthcare.adapters.AppointmentsAdapter
import com.healthcare.adapters.CartAdapter
import com.healthcare.adapters.DoctorAdapter
import com.healthcare.adapters.LabTestAdapter
import com.healthcare.adapters.MedsAdapter
import com.healthcare.models.Appointment
import com.healthcare.models.Doctor
import com.healthcare.models.LabTestPackage
import com.healthcare.models.Medicine

class HomeFragment : Fragment() {
    private var doctorsList: List<Doctor> = listOf()
    private var appointmentsList: List<Appointment> = emptyList()

    private lateinit var appointmentsAdapter: AppointmentsAdapter
    private lateinit var doctorsAdapter: DoctorAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val appointmentsRecyclerView =
            view.findViewById<RecyclerView>(R.id.appointmentsRecyclerView)
        val doctorsRecyclerView = view.findViewById<RecyclerView>(R.id.doctorsRecyclerView)
        val viewAllDoctorsTextView = view.findViewById<TextView>(R.id.viewAllDoctorsTextView)

        appointmentsAdapter = AppointmentsAdapter(appointments = appointmentsList)
        (activity as? MainActivity)?.fetchAppointmentsAndUpdateUI { appointments ->
            appointmentsList = appointments
            appointmentsAdapter.appointments = appointmentsList
            appointmentsAdapter.notifyDataSetChanged()
        }

        // Check if doctorsList is not empty before creating the adapter
        doctorsAdapter = if (doctorsList.isNotEmpty()) {
            DoctorAdapter(doctorList = doctorsList.subList(0, minOf(2, doctorsList.size)))
        } else {
            DoctorAdapter(doctorList = emptyList())
        }
        (activity as? MainActivity)?.fetchDoctorsAndUpdateUI { doctors ->
            doctorsList = doctors
            doctorsAdapter.doctorList = doctorsList.subList(0, minOf(2, doctorsList.size))
            doctorsAdapter.notifyDataSetChanged()
        }

        appointmentsRecyclerView.adapter = appointmentsAdapter
        doctorsRecyclerView.adapter = doctorsAdapter

        appointmentsRecyclerView.layoutManager = LinearLayoutManager(context)
        doctorsRecyclerView.layoutManager = LinearLayoutManager(context)

        viewAllDoctorsTextView.setOnClickListener {
            val mainActivity = activity as? MainActivity
            mainActivity?.openDoctorsFragment()
        }

        return view
    }

}

class LabPackagesFragment : Fragment() {
    private lateinit var labPackagesRecyclerView: RecyclerView
    private lateinit var labPackagesAdapter: LabTestAdapter
    private var labPackagesList: List<LabTestPackage> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lab_packages, container, false)

        labPackagesRecyclerView = view.findViewById(R.id.labPackagesRecyclerView)
        labPackagesAdapter = LabTestAdapter(labPackagesList)
        (activity as? MainActivity)?.fetchLabTestPackagesAndUpdateUI { labs ->
            labPackagesList = labs
            labPackagesAdapter.labTestList = labPackagesList
            labPackagesAdapter.notifyDataSetChanged()
        }

        labPackagesRecyclerView.layoutManager = LinearLayoutManager(context)
        labPackagesRecyclerView.adapter = labPackagesAdapter

        return view
    }

}

class DoctorsFragment : Fragment() {
    private var doctorsList: List<Doctor> = listOf()
    private lateinit var doctorsAdapter: DoctorAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doctors, container, false)
        val doctorsRecyclerView = view.findViewById<RecyclerView>(R.id.allDoctorsRecyclerView)

        // Check if doctorsList is not empty before creating the adapter
        doctorsAdapter = DoctorAdapter(doctorList = emptyList())

        (activity as? MainActivity)?.fetchDoctorsAndUpdateUI { doctors ->
            doctorsList = doctors
            doctorsAdapter.doctorList = doctorsList
            doctorsAdapter.notifyDataSetChanged()
        }

        doctorsRecyclerView.adapter = doctorsAdapter
        doctorsRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

}

class MedsFragment : Fragment() {
    private var medsList: List<Medicine> = listOf()
    private lateinit var medsAdapter: MedsAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meds, container, false)
        val medsRecyclerView = view.findViewById<RecyclerView>(R.id.allMedsRecyclerView)
        val tvMyCart = view.findViewById<TextView>(R.id.tvMyCart)

        tvMyCart.setOnClickListener {
            val mainActivity = activity as? MainActivity
            mainActivity?.openMyCartFragment()
        }

        medsAdapter = MedsAdapter(activity as MainActivity, meds = emptyList())

        (activity as? MainActivity)?.fetchMedicinesAndUpdateUI { meds ->
            medsList = meds
            medsAdapter.meds = medsList
            medsAdapter.notifyDataSetChanged()
        }

        medsRecyclerView.adapter = medsAdapter
        medsRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

}

class MyCartFragment : Fragment() {
    private var medsList: List<Medicine> = listOf()
    private lateinit var cartsAdapter: CartAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_cart, container, false)
        val cartRecyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)

        cartsAdapter = CartAdapter(cartItems = emptyList(), context = requireContext())

        (activity as? MainActivity)?.fetchCartAndUpdateUI { meds ->
            medsList = meds
            cartsAdapter.cartItems = medsList
            cartsAdapter.notifyDataSetChanged()
        }

        cartRecyclerView.adapter = cartsAdapter
        cartRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }


}




