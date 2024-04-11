package com.healthcare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.healthcare.R
import com.healthcare.models.Appointment
import com.healthcare.models.Medicine

class MedsAdapter(private val context: Context, var meds: List<Medicine>) :
    RecyclerView.Adapter<MedsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medNameTextView: TextView = itemView.findViewById(R.id.medicineNameTextView)
        val medPriceTextView: TextView = itemView.findViewById(R.id.medicinePriceTextView)
        val medDescriptionTextView: TextView =
            itemView.findViewById(R.id.medicineDescriptionTextView)
        val addToCartButton: Button = itemView.findViewById(R.id.addToCartButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.medicine_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val med = meds[position]
        holder.medNameTextView.text = med.name
        holder.medPriceTextView.text = "Price: ${med.price}"
        holder.medDescriptionTextView.text = med.dosage

        holder.addToCartButton.setOnClickListener {
            val selectedMedicine = meds[position]
            addToCart(selectedMedicine)
        }
    }

    override fun getItemCount(): Int {
        return meds.size
    }

    private fun getUserIdFromSharedPreferences(): String? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    private fun addToCart(selectedMedicine: Medicine) {
        val userId = getUserIdFromSharedPreferences()
        val databaseRef = Firebase.database.getReference("healthcare/users/$userId/medicine_cart")

        // Check if the medicine already exists in the cart
        databaseRef.orderByChild("name").equalTo(selectedMedicine.name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                         Toast.makeText(context, "Medicine already in cart", Toast.LENGTH_LONG)
                            .show()
                    } else {
                         val cartItemId = databaseRef.push().key ?: ""
                        databaseRef.child(cartItemId).setValue(selectedMedicine)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Medicine added to cart",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Failed to add medicine to cart",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error checking medicine in cart: ${databaseError.message}")
                }
            })
    }

}

