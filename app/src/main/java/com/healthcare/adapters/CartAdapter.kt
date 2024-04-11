package com.healthcare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
 import com.healthcare.R
import com.healthcare.models.Medicine

class CartAdapter(val context: Context, var cartItems: List<Medicine>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.medNameTextView.text = cartItem.name
        holder.medPriceTextView.text = "Price: ${cartItem.price}"
        holder.medDescriptionTextView.text = cartItem.dosage

        holder.removeFromCartButton.setOnClickListener {
            val selectedMedicine = cartItems[position]
            removeFromCart(selectedMedicine)
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val removeFromCartButton: CardView = itemView.findViewById(R.id.removeFromCartButton)

        val medNameTextView: TextView = itemView.findViewById(R.id.medicineNameTextView)
        val medPriceTextView: TextView = itemView.findViewById(R.id.medicinePriceTextView)
        val medDescriptionTextView: TextView =
            itemView.findViewById(R.id.medicineDescriptionTextView)
    }

    private fun getUserIdFromSharedPreferences(): String? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    private fun removeFromCart(selectedMedicine: Medicine) {
        val userId = getUserIdFromSharedPreferences()
        val databaseRef = Firebase.database.getReference("healthcare/users/$userId/medicine_cart")

        val query = databaseRef.orderByChild("name").equalTo(selectedMedicine.name)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val cartItemId = snapshot.key
                        if (cartItemId != null) {
                            databaseRef.child(cartItemId).removeValue()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            "Medicine removed from cart",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        // Remove the medicine from the cartItems list
                                        cartItems = cartItems.filter { it.name != selectedMedicine.name }
                                        notifyDataSetChanged()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Failed to remove medicine from cart",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    }
                } else {
                    Toast.makeText(context, "Medicine not found in cart", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Error: ${databaseError.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}
