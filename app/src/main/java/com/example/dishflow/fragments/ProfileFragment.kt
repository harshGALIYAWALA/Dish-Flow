package com.example.dishflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dishflow.R
import com.example.dishflow.databinding.FragmentProfileBinding
import com.example.dishflow.models.UserUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setUpData()

        binding.saveInfoBtn.setOnClickListener{
            val name = binding.name.text.toString()
            val address = binding.address.text.toString()
            val phone = binding.phone.text.toString()
            val email = binding.email.text.toString()

            updateInfo(name, address, phone, email)
        }


        return binding.root
    }

    private fun updateInfo(name: String, address: String, phone: String, email: String) {
        val userId = auth.currentUser?.uid
        val userRef = database.reference.child("user").child(userId!!)

        if(userId != null) {
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "phone" to phone,
                "email" to email
            )
            userRef.setValue(userData).addOnSuccessListener {
                Toast.makeText(context, "profile is updated in database", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{
                    Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setUpData() {

        val userId = auth.currentUser?.uid

        if(userId != null) {
            val userRef = database.reference.child("user").child(userId)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userProfile = snapshot.getValue(UserUserModel::class.java)
                        if(userProfile != null) {
                           binding.name.setText(userProfile.name)
                            binding.address.setText(userProfile.address)
                            binding.phone.setText(userProfile.phone)
                            binding.email.setText(userProfile.email)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }


}