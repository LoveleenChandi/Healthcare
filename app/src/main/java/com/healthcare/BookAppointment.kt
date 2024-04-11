package com.healthcare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.healthcare.models.Appointment

class BookAppointment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        // Initialize views
        val doctorNameTextView = findViewById<TextView>(R.id.doctorNameTextView)
        val doctorSpecialtyTextView = findViewById<TextView>(R.id.doctorSpecialtyTextView)

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val bookAppointmentButton = findViewById<Button>(R.id.bookAppointmentButton)

        // Retrieve doctor data from Intent extras
        val doctorName = intent.getStringExtra("DOCTOR_NAME")
        val doctorSpecialty = intent.getStringExtra("DOCTOR_SPECIALTY")

        // Set doctor data to TextViews
        doctorNameTextView.text = "Doctor Name: $doctorName"
        doctorSpecialtyTextView.text = "Specialty: $doctorSpecialty"

        bookAppointmentButton.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val dayOfMonth = datePicker.dayOfMonth

            createAppointment(
                userId = getUserIdFromSharedPreferences() ?: "",
                doctorName = doctorName ?: "",
                doctorSpecialty = doctorSpecialty ?: "",
                year = year,
                month = month,
                dayOfMonth = dayOfMonth,
                onSuccess = {
                    Toast.makeText(
                        this@BookAppointment, "Appointment booked successfully!",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(Intent(this@BookAppointment, MainActivity::class.java))
                    finish()
                },
                onFailure = {
                    Toast.makeText(
                        this@BookAppointment, "Booking Failed!!: $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun getUserIdFromSharedPreferences(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    fun createAppointment(
        userId: String,
        doctorName: String,
        doctorSpecialty: String,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance()
        val appointmentsRef = database.getReference("healthcare/users/$userId/appointments")

        val appointmentId = appointmentsRef.push().key ?: ""

        val appointmentTime = "$year-$month-$dayOfMonth"

        // Create a new Appointment object
        val appointment = Appointment(
            id = appointmentId,
            doctorName = doctorName,
            specialty = doctorSpecialty,
            appointmentTime = appointmentTime
        )

        appointmentsRef.child(appointmentId).setValue(appointment)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception.message ?: "Unknown error")
            }
    }
}

