package com.healthcare

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.healthcare.models.Appointment
import com.healthcare.models.Doctor
import com.healthcare.models.LabTestPackage
import com.healthcare.models.Medicine

class MainActivity : AppCompatActivity() {
    private val doctorsList = mutableListOf<Doctor>()
    private val medicinesList = mutableListOf<Medicine>()
    private val cartList = mutableListOf<Medicine>()
    private val labTestPackagesList = mutableListOf<LabTestPackage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uploadLabTestPackages()
        uploadMedicineList()
        uploadDoctorList()

        fetchAllDoctorsFromDatabase(callback = {
            Log.d("docs", it.toString())
        })
        fetchAllLabTestPackagesFromDatabase(callback = {
            Log.d("labs", it.toString())
        })
        fetchAllMedicinesFromDatabase(callback = {
            Log.d("meds", it.toString())
        })
        fetchAllAppointmentsFromDatabase(callback = {
            Log.d("apps", it.toString())
        })
        fetchAllMedicinesFromDatabase(callback = {
            Log.d("meds", it.toString())
        })

        val homeFragment = HomeFragment()
        val doctorsFragment = DoctorsFragment()
        val labTestsFragment = LabPackagesFragment()
        val medsFragment = MedsFragment()

        // Load HomeFragment by default
        replaceFragment(homeFragment)

        val homeButton = findViewById<Button>(R.id.homeButton)
        val labPackagesButton = findViewById<Button>(R.id.labPackagesButton)
        val doctorsButton = findViewById<Button>(R.id.doctorsButton)
        val medsButton = findViewById<Button>(R.id.medsButton)

        homeButton.setOnClickListener {
            replaceFragment(homeFragment)
        }

        labPackagesButton.setOnClickListener {
            replaceFragment(labTestsFragment)
        }

        doctorsButton.setOnClickListener {
            replaceFragment(doctorsFragment)
        }

        medsButton.setOnClickListener {
            replaceFragment(medsFragment)
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentFrame, fragment)
            .commit()
    }

    fun openDoctorsFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contentFrame, DoctorsFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun openMyCartFragment() {
        val transaction = supportFragmentManager.beginTransaction()
         transaction.replace(R.id.contentFrame, MyCartFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getUserIdFromSharedPreferences(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    private fun uploadLabTestPackages() {
        val utils = Utils()
        val labTestPackages = utils.labTestPackages

        val database = Firebase.database
        val ref = database.getReference("healthcare/lab_test_packages")

        // Check if data already exists
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // If data doesn't exist, upload it
                    labTestPackages.forEachIndexed { index, labTestPackage ->
                        val packageRef = ref.push()
                        packageRef.setValue(labTestPackage)
                            .addOnSuccessListener {
                                println("LabTestPackage ${index + 1} uploaded successfully.")
                            }
                            .addOnFailureListener { e ->
                                println("Error uploading LabTestPackage ${index + 1}: $e")
                            }
                    }
                } else {
                    println("Lab test packages already exist in the database.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error checking lab test packages: ${databaseError.message}")
            }
        })
    }

    private fun uploadMedicineList() {
        val utils = Utils()
        val medicines = utils.medicines

        val database = Firebase.database
        val ref = database.getReference("healthcare/medicines")

        // Check if data already exists
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // If data doesn't exist, upload it
                    medicines.forEachIndexed { index, medicine ->
                        val medicineRef = ref.push()
                        medicineRef.setValue(medicine)
                            .addOnSuccessListener {
                                println("Medicine ${index + 1} uploaded successfully.")
                            }
                            .addOnFailureListener { e ->
                                println("Error uploading Medicine ${index + 1}: $e")
                            }
                    }
                } else {
                    println("Medicine list already exists in the database.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error checking medicine list: ${databaseError.message}")
            }
        })
    }

    private fun uploadDoctorList() {
        val utils = Utils()
        val doctors = utils.doctors

        val database = Firebase.database
        val ref = database.getReference("healthcare/doctors")

        // Check if data already exists
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // If data doesn't exist, upload it
                    doctors.forEachIndexed { index, doctor ->
                        val doctorRef = ref.push()
                        doctorRef.setValue(doctor)
                            .addOnSuccessListener {
                                println("Doctor ${index + 1} uploaded successfully.")
                            }
                            .addOnFailureListener { e ->
                                println("Error uploading Doctor ${index + 1}: $e")
                            }
                    }
                } else {
                    println("Doctor list already exists in the database.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error checking doctor list: ${databaseError.message}")
            }
        })
    }

    private fun fetchAllDoctorsFromDatabase(callback: (List<Doctor>) -> Unit) {
        val database = Firebase.database
        val ref = database.getReference("healthcare/doctors")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot in dataSnapshot.children) {
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    val specialty = snapshot.child("specialty").getValue(String::class.java) ?: ""
                    val qualifications =
                        snapshot.child("qualifications").getValue(String::class.java) ?: ""
                    val availability =
                        snapshot.child("availability").getValue(String::class.java) ?: ""
                    val reviewsList = snapshot.child("reviews").children.map {
                        it.getValue(String::class.java) ?: ""
                    }

                    val doctor = Doctor(name, specialty, qualifications, availability, reviewsList)
                    doctorsList.add(doctor)
                }
                callback(doctorsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error fetching doctors: ${databaseError.message}")
            }
        })
    }

    private fun fetchAllMedicinesFromDatabase(callback: (List<Medicine>) -> Unit) {
        val database = Firebase.database
        val ref = database.getReference("healthcare/medicines")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    val dosage = snapshot.child("dosage").getValue(String::class.java) ?: ""
                    val price = snapshot.child("price").getValue(Double::class.java) ?: 0.0
                    val manufacturer =
                        snapshot.child("manufacturer").getValue(String::class.java) ?: ""
                    val medicine = Medicine(name, dosage, price, manufacturer)
                    medicinesList.add(medicine)
                }
                callback(medicinesList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error fetching medicines: ${databaseError.message}")
            }
        })
    }

    private fun fetchAllLabTestPackagesFromDatabase(callback: (List<LabTestPackage>) -> Unit) {
        val database = Firebase.database
        val ref = database.getReference("healthcare/lab_test_packages")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    val testsList = snapshot.child("tests").children.map {
                        it.getValue(String::class.java) ?: ""
                    }
                    val price = snapshot.child("price").getValue(String::class.java) ?: ""
                    val instructions =
                        snapshot.child("instructions").getValue(String::class.java) ?: ""
                    val labTestPackage = LabTestPackage(name, testsList, price, instructions)
                    labTestPackagesList.add(labTestPackage)
                }
                callback(labTestPackagesList)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error fetching lab test packages: ${databaseError.message}")
            }
        })
    }

    private fun fetchAllAppointmentsFromDatabase(callback: (List<Appointment>) -> Unit) {
        val database = Firebase.database
        val ref =
            database.getReference("healthcare/users/${getUserIdFromSharedPreferences()}/appointments")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val appointmentsList = mutableListOf<Appointment>()

                for (snapshot in dataSnapshot.children) {
                    val id = snapshot.child("id").getValue(String::class.java) ?: ""
                    val doctorName = snapshot.child("doctorName").getValue(String::class.java) ?: ""
                    val specialty = snapshot.child("specialty").getValue(String::class.java) ?: ""
                    val appointmentTime =
                        snapshot.child("appointmentTime").getValue(String::class.java) ?: ""

                    val appointment = Appointment(id, doctorName, specialty, appointmentTime)
                    appointmentsList.add(appointment)
                }

                callback(appointmentsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error fetching appointments: ${databaseError.message}")
            }
        })
    }

    private fun fetchCartFromDatabase(callback: (List<Medicine>) -> Unit) {
        val database = Firebase.database
        val ref = database.getReference("healthcare/users/${getUserIdFromSharedPreferences()}/medicine_cart")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cartList = mutableListOf<Medicine>() // Create a new list

                for (snapshot in dataSnapshot.children) {
                    val name = snapshot.child("name").getValue(String::class.java) ?: ""
                    val dosage = snapshot.child("dosage").getValue(String::class.java) ?: ""
                    val price = snapshot.child("price").getValue(Double::class.java) ?: 0.0
                    val manufacturer = snapshot.child("manufacturer").getValue(String::class.java) ?: ""

                    val medicine = Medicine(name, dosage, price, manufacturer)
                    cartList.add(medicine)
                }

                callback(cartList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error fetching medicines: ${databaseError.message}")
            }
        })
    }


    fun fetchDoctorsAndUpdateUI(callback: (List<Doctor>) -> Unit) {
        fetchAllDoctorsFromDatabase { doctors ->
            callback(doctors)
        }
    }

    fun fetchMedicinesAndUpdateUI(callback: (List<Medicine>) -> Unit) {
        fetchAllMedicinesFromDatabase { medicines ->
            callback(medicines)
        }
    }

    fun fetchCartAndUpdateUI(callback: (List<Medicine>) -> Unit) {
        fetchCartFromDatabase { medicines ->
            callback(medicines)
        }
    }

    fun fetchLabTestPackagesAndUpdateUI(callback: (List<LabTestPackage>) -> Unit) {
        fetchAllLabTestPackagesFromDatabase { labTestPackages ->
            callback(labTestPackages)
        }
    }

    fun fetchAppointmentsAndUpdateUI(callback: (List<Appointment>) -> Unit) {
        fetchAllAppointmentsFromDatabase { appointments ->
            callback(appointments)
        }
    }

}